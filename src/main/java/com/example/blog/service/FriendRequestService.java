package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.FriendDTO;
import com.example.blog.entity.FriendRequest;

import java.util.List;

public interface FriendRequestService extends IService<FriendRequest> {
//    List<UserProfileDTO> getRecommendFriends(int userId);
    int addRequest(FriendRequest friendRequest);
    int replyFriendRequest(int userId, int friendId, boolean reply);
    List<FriendDTO> FriendRequestList(int userId);
}
