package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dto.GuestBookReplyDTO;
import com.example.blog.entity.GuestBookReply;

import java.util.List;

public interface GuestBookReplyMapper extends BaseMapper<GuestBookReply> {
    List<GuestBookReplyDTO> selectGuestBookReplies(int guestBookId);
}
