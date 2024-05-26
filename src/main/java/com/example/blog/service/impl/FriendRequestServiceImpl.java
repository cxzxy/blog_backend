package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.FriendRequest;
import com.example.blog.entity.Friendship;
import com.example.blog.entity.User;
import com.example.blog.mapper.FriendRequestMapper;
import com.example.blog.mapper.FriendshipMapper;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class FriendRequestServiceImpl extends ServiceImpl<FriendRequestMapper, FriendRequest> implements FriendRequestService {

    @Autowired
    private FriendRequestMapper friendRequestMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Override
    public int addRequest(FriendRequest friendRequest) {
        //判断好友会否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id", friendRequest.getFriendId());
        User friendUser = userMapper.selectOne(userQueryWrapper);
        if (friendUser == null) {
            return -1;
        }
        //根据user_id和friend_id判断是否添加过
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", friendRequest.getUserId());
        queryWrapper.eq("friend_id", friendRequest.getFriendId());
        FriendRequest pastFriendRequest = friendRequestMapper.selectOne(queryWrapper);
        if (pastFriendRequest != null) {
            //如果目前是好友关系，返回0
            if (pastFriendRequest.getStatus().equals("accepted")) {
                return 0;
            }
            //如果目前是pending状态，返回1
            else if (pastFriendRequest.getStatus().equals("pending")) {
                return 1;
            }
            //如果目前是rejected状态，更新状态为pending
            else {
                pastFriendRequest.setStatus("pending");
                this.update(pastFriendRequest, queryWrapper);
                return 2;
            }
        }
        friendRequest.setStatus("pending");
        Date date = new Date();
        friendRequest.setCreatedAt(date);
        friendRequestMapper.insert(friendRequest);
        return 3;
    }

    @Override
    public int replyFriendRequest(int userId, int friendId, boolean reply) {
        //判断好友是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id", friendId);
        User friendUser = userMapper.selectOne(userQueryWrapper);
        if (friendUser == null) {
            return -1;
        }
        //添加好友
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", friendId);
        queryWrapper.eq("friend_id", userId);
        FriendRequest friendRequest = friendRequestMapper.selectOne(queryWrapper);
        if (reply) {
            friendRequest.setStatus("accepted");

            //构建好友关系
            Friendship friendship = new Friendship();
            friendship.setUserId(userId);
            friendship.setFriendId(friendId);
            Date date = new Date();
            friendship.setCreatedAt(date);
            friendshipMapper.insert(friendship);

            friendship.setUserId(friendId);
            friendship.setFriendId(userId);
            friendshipMapper.insert(friendship);
        } else {
            friendRequest.setStatus("rejected");
        }
        this.update(friendRequest, queryWrapper);
        return 0;
    }

    @Override
    public boolean deleteFriend(int userId, int friendId) {
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("friend_user_id", friendId);
//        if()
        return true;
    }
}
