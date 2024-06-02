package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.GuestBookDataDTO;
import com.example.blog.entity.GuestBookReply;

public interface GuestBookReplyService extends IService<GuestBookReply> {
    GuestBookDataDTO getGuestBookReply(int guestBookId, int userId);
    int createGuestBookReply(GuestBookReply guestBookReply);
    int deleteGuestBookReply(int userId, int guestBookReplyId);
}
