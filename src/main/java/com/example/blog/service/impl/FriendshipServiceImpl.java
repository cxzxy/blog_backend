package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.FriendDTO;
import com.example.blog.dto.FriendRecommandDTO;
import com.example.blog.entity.Friendship;
import com.example.blog.mapper.FriendshipMapper;
import com.example.blog.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl extends ServiceImpl<FriendshipMapper, Friendship> implements FriendshipService {

    @Autowired
    private FriendshipMapper friendshipMapper;


    @Override
    public List<FriendRecommandDTO> selectFriendsByUserId(int userId) {
        return friendshipMapper.selectRecommendFriend(userId);
    }

    @Override
    public List<FriendDTO> selectAllFriends(int userId) {
        return friendshipMapper.selectAllFriends(userId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        QueryWrapper<Friendship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("friend_id", friendId);
        friendshipMapper.delete(queryWrapper);
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", friendId);
        queryWrapper.eq("friend_id", userId);
        friendshipMapper.delete(queryWrapper);
    }

    @Override
    public void updateFriendRemark(Friendship friendship){
        QueryWrapper<Friendship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", friendship.getUserId());
        queryWrapper.eq("friend_id", friendship.getFriendId());
        friendshipMapper.update(friendship, queryWrapper);
    }
}
