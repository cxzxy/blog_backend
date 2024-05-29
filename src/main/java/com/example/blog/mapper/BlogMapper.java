package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dto.BlogDTO;
import com.example.blog.dto.PageDTO;
import com.example.blog.entity.Blog;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {
    List<BlogDTO> BlogList(PageDTO pageDTO);
    List<BlogDTO> BlogListByUserIds(int offset, int size, List<Integer> list);
}
