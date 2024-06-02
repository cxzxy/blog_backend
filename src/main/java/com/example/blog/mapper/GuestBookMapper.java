package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dto.GuestBookDTO;
import com.example.blog.entity.GuestBook;

import java.util.List;

public interface GuestBookMapper extends BaseMapper<GuestBook> {
    List<GuestBookDTO> selectGuestBooks(int userId, int otherId);
    GuestBookDTO selectGuestBook(int guestBookId);
}
