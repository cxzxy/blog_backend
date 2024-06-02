package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.GuestBookDTO;
import com.example.blog.dto.GuestBookDataDTO;
import com.example.blog.dto.GuestBookReplyDTO;
import com.example.blog.entity.GuestBookReply;
import com.example.blog.mapper.GuestBookMapper;
import com.example.blog.mapper.GuestBookReplyMapper;
import com.example.blog.service.AccessControlService;
import com.example.blog.service.GuestBookReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestBookReplyServiceImpl extends ServiceImpl<GuestBookReplyMapper, GuestBookReply> implements GuestBookReplyService {

    @Autowired
    private GuestBookReplyMapper guestBookReplyMapper;

    @Autowired
    private GuestBookMapper guestBookMapper;

    @Autowired
    private AccessControlService accessControlService;

    @Override
    public GuestBookDataDTO getGuestBookReply(int guestBookId, int userId){

        int ownerId = guestBookMapper.selectById(guestBookId).getUserId();
        int status = accessControlService.control(userId, ownerId);
        if(status != 1) return null;

        GuestBookDTO guestBookDTO = guestBookMapper.selectGuestBook(guestBookId);
        if(guestBookDTO == null)  return null;
        List<GuestBookReplyDTO> guestBookReplyDTOS = guestBookReplyMapper.selectGuestBookReplies(guestBookId);
        boolean isOneself = userId == guestBookMapper.selectById(guestBookId).getUserId();
        return GuestBookDataDTO.builder()
                .guestBook(guestBookDTO)
                .guestBookReplyList(guestBookReplyDTOS)
                .isOneSelf(isOneself)
                .build();
    }

    @Override
    public int createGuestBookReply(GuestBookReply guestBookReply){
        int guestBookId = guestBookReply.getGuestBookId();
        int ownerId = guestBookMapper.selectById(guestBookId).getUserId();
        int status = accessControlService.control(guestBookReply.getSendUserId(), ownerId);
        if(status != 1) return 0;
        return guestBookReplyMapper.insert(guestBookReply);
    }

    @Override
    public int deleteGuestBookReply(int userId, int guestBookReplyId){
        GuestBookReply guestBookReply = guestBookReplyMapper.selectById(guestBookReplyId);
        if(guestBookReply == null) return 0;
        int guestBookId = guestBookReply.getGuestBookId();
        int ownerId = guestBookMapper.selectById(guestBookId).getUserId();
        if(ownerId != userId) return 0;
        return guestBookReplyMapper.deleteById(guestBookReplyId);
    }
}
