package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dto.FriendDTO;
import com.example.blog.entity.FriendRequest;

import java.util.List;

public interface FriendRequestMapper extends BaseMapper<FriendRequest> {
    List<FriendDTO> FriendRequestList(int userId);
}
