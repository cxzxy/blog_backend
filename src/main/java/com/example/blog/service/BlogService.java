package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.BlogDTO;
import com.example.blog.dto.PageDTO;
import com.example.blog.entity.Blog;

import java.util.List;

public interface BlogService extends IService<Blog>{
    //发布博客
    void publishBlog(Blog blog);
    int deleteBlog(int userId, int blogId);
    int updateBlog(Blog blog);
    Blog getBlog(int userId, int blogId);
    List<BlogDTO> getBlogList(PageDTO pageDTO);
    List<BlogDTO> getFriendsBlogList(PageDTO pageDTO);
}
