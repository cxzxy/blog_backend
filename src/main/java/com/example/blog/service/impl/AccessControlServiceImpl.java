package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.SpaceDTO;
import com.example.blog.entity.*;
import com.example.blog.mapper.*;
import com.example.blog.service.AccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessControlServiceImpl extends ServiceImpl<AccessControlMapper, AccessControl> implements AccessControlService {

    @Autowired
    private AccessControlMapper accessControlMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private GuestBookMapper guestBookMapper;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Override
    public AccessControl getAccessControl(int userId) {
        QueryWrapper<AccessControl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return getOne(queryWrapper);
    }

    @Override
    public int updateAccessControl(AccessControl accessControl) {
        return accessControlMapper.updateById(accessControl);
    }

    @Override
    public SpaceDTO getSpace(int userId, int otherId) {
        SpaceDTO spaceDTO = new SpaceDTO();
        spaceDTO.setUserId(otherId);
        QueryWrapper<Profile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", otherId);
        Profile profile = profileMapper.selectOne(queryWrapper);
        spaceDTO.setUserName(profile.getUserName());
        spaceDTO.setAvatarUrl(profile.getAvatarUrl());
        //查询博客数量
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq("user_id", otherId);
        int blogCount = blogMapper.selectCount(blogQueryWrapper);
        spaceDTO.setBlogNum(blogCount);
        //查询相册数量
        QueryWrapper<Album> albumQueryWrapper = new QueryWrapper<>();
        albumQueryWrapper.eq("user_id", otherId);
        int albumCount = albumMapper.selectCount(albumQueryWrapper);
        spaceDTO.setAlbumNum(albumCount);
        //查询留言数量
        QueryWrapper<GuestBook> guestBookQueryWrapper = new QueryWrapper<>();
        guestBookQueryWrapper.eq("user_id", otherId);
        int guestBookCount = guestBookMapper.selectCount(guestBookQueryWrapper);
        spaceDTO.setGuestBookNum(guestBookCount);
        return spaceDTO;
    }

    @Override
    public int control(int userId, int otherId) {
        if(userId == otherId) {
            return 1;
        }
        AccessControl accessControl = getAccessControl(otherId);
        //空间不存在
        if(accessControl == null) {
            return -1;
        }
        //空间权限为公开
        if(accessControl.getPermissionType().equals("public")) {
            return 1;
        }
        //空间权限为好友
        if(accessControl.getPermissionType().equals("friends_only")) {
            //判断是否为好友
            System.out.println("userId: " + userId);
            List<Integer> friendIds = friendshipMapper.selectFriendsByUserId(otherId);
            if(friendIds.contains(userId)) {
                return 1;
            }else {
                return 0;
            }
        }
        if(accessControl.getPermissionType().equals("private")){
            return 0;
        }
        return -2;
    }
}
