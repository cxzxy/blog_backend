package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.FriendRequest;

public interface FriendRequestService extends IService<FriendRequest> {
//    List<UserProfileDTO> getRecommendFriends(int userId);
    int addRequest(FriendRequest friendRequest);
    int replyFriendRequest(int userId, int friendId, boolean reply);
    boolean deleteFriend(int userId, int friendId);
}
