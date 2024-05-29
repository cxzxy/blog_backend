package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.UserProfileDTO;
import com.example.blog.entity.Friendship;
import com.example.blog.entity.Profile;
import com.example.blog.mapper.FriendshipMapper;
import com.example.blog.mapper.ProfileMapper;
import com.example.blog.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile> implements ProfileService{

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private FriendshipMapper friendshipMapper;

    /**
     * 获取用户档案
     * @param userId 用户ID
     * @return 用户档案
     */
    @Override
    public UserProfileDTO getProfileByUserId(int userId, int otherId) {
        //是自己
        if(otherId == 0 || userId == otherId){
            UserProfileDTO userProfileDTO = profileMapper.getProfileByUserId(userId);
            userProfileDTO.setIsFriend(false);
            userProfileDTO.setIsOneself(true);
            userProfileDTO.setRemarks(userProfileDTO.getUserName());
            return userProfileDTO;
        }
        List<Integer> userIds = friendshipMapper.selectFriendsByUserId(userId);
        UserProfileDTO userProfileDTO = profileMapper.getProfileByUserId(otherId);
        //不存在该用户
        if(userProfileDTO == null){
            return null;
        }
        //是好友
        if(userIds.contains(userProfileDTO.getUserId())){
            userProfileDTO.setIsFriend(true);
            userProfileDTO.setIsOneself(false);
            // 如果是好友，获取好友备注
            QueryWrapper<Friendship> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("friend_id", userProfileDTO.getUserId());
            Friendship friendship = friendshipMapper.selectOne(queryWrapper);
            userProfileDTO.setRemarks(friendship.getRemarks());
        }else {
            userProfileDTO.setIsFriend(false);
            userProfileDTO.setIsOneself(false);
            userProfileDTO.setRemarks(userProfileDTO.getUserName());
        }
        return userProfileDTO;
    }

    /**
     * 修改用户档案
     * @param profile 用户档案
     * @return 修改后的用户档案
     */
    @Override
    public Profile updateProfile(Profile profile) {
        profileMapper.updateById(profile);
        return profile;
    }

    /**
     * 更新用户头像
     * @param profile 用户档案
     */
    @Override
    public void updateAvatarUrl(Profile profile) {
        UpdateWrapper<Profile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", profile.getUserId());
        Profile user = new Profile();
        user.setAvatarUrl(profile.getAvatarUrl());
        this.update(user, updateWrapper);
    }

    /**
     * 通过账号获取用户档案
     * @param account 用户账号
     * @return 用户档案
     */
    @Override
    public UserProfileDTO getProfileByAccount(int userId, String account){
        List<Integer> userIds = friendshipMapper.selectFriendsByUserId(userId);
        UserProfileDTO userProfileDTO = profileMapper.getProfileByAccount(account);
        if(userProfileDTO == null){
            return null;
        }
        if(userIds.contains(userProfileDTO.getUserId())){
            userProfileDTO.setIsFriend(true);
            // 如果是好友，获取好友备注
            QueryWrapper<Friendship> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("friend_id", userProfileDTO.getUserId());
            Friendship friendship = friendshipMapper.selectOne(queryWrapper);
            userProfileDTO.setRemarks(friendship.getRemarks());
        }else {
            userProfileDTO.setIsFriend(false);
        }
        return userProfileDTO;
    }
}
