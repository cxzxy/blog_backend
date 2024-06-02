package com.example.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.GuestBookDTO;
import com.example.blog.entity.GuestBook;
import com.example.blog.mapper.GuestBookMapper;
import com.example.blog.service.AccessControlService;
import com.example.blog.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestBookServiceImpl extends ServiceImpl<GuestBookMapper, GuestBook> implements GuestBookService {

    @Autowired
    private GuestBookMapper guestBookMapper;

    @Autowired
    private AccessControlService accessControlService;

    @Override
    public int createGuestBook(GuestBook guestBook) {
        return guestBookMapper.insert(guestBook);
    }

    @Override
    public List<GuestBookDTO> getGuestBooks(int userId, int otherId) {
        int status = accessControlService.control(userId, otherId);
        if (status != 1) {
            return null;
        }
        return guestBookMapper.selectGuestBooks(userId, otherId);
    }

    @Override
    public int deleteGuestBook(int userId, int guestBookId) {
        //判断是否是自己的留言
        GuestBook guestBook = guestBookMapper.selectById(guestBookId);
        if (guestBook==null||(guestBook.getUserId() != userId)) {
            return 0;
        }
        return guestBookMapper.deleteById(guestBookId);
    }
}
