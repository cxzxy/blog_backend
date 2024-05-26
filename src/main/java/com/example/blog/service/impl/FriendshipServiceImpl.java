package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Friendship;
import com.example.blog.mapper.FriendshipMapper;
import com.example.blog.service.FriendshipService;
import org.springframework.stereotype.Service;

@Service
public class FriendshipServiceImpl extends ServiceImpl<FriendshipMapper, Friendship> implements FriendshipService {


    @Override
    public boolean addFriend(int userId, int friendId) {
        return false;
    }
}
