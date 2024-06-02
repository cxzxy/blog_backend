package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.GuestBookDTO;
import com.example.blog.entity.GuestBook;

import java.util.List;

public interface GuestBookService extends IService<GuestBook> {
    int createGuestBook(GuestBook guestBook);
    List<GuestBookDTO> getGuestBooks(int userId, int otherId);
    int deleteGuestBook(int userId, int guestBookId);
}
