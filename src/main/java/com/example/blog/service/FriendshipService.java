package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.FriendDTO;
import com.example.blog.dto.FriendRecommandDTO;
import com.example.blog.entity.Friendship;

import java.util.List;

public interface FriendshipService extends IService<Friendship> {
    List<FriendRecommandDTO> selectFriendsByUserId(int userId);
    List<FriendDTO> selectAllFriends(int userId);
    void deleteFriend(int userId, int friendId);
    void updateFriendRemark(Friendship friendship);
}
