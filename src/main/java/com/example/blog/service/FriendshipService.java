package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Friendship;

public interface FriendshipService extends IService<Friendship> {
    boolean addFriend(int userId, int friendId);
}
