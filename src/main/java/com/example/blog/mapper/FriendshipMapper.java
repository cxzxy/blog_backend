package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dto.FriendDTO;
import com.example.blog.dto.FriendRecommandDTO;
import com.example.blog.entity.Friendship;

import java.util.List;

public interface FriendshipMapper extends BaseMapper<Friendship> {
    List<FriendRecommandDTO> selectRecommendFriend(int userId);
    List<Integer> selectFriendsByUserId(int userId);
    List<FriendDTO> selectAllFriends(int userId);
}
