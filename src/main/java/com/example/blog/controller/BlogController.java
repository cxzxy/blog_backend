package com.example.blog.controller;


import com.example.blog.dto.BlogDTO;
import com.example.blog.dto.PageDTO;
import com.example.blog.entity.Blog;
import com.example.blog.service.AccessControlService;
import com.example.blog.service.BlogService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AccessControlService accessControlService;

    //发布博客
    @ApiOperation(value = "发布博客")
    @PostMapping("/blog")
    public ResultUtil<Object> publishBlog(HttpServletRequest request, @RequestBody Blog blog) {
        int userId = (int) request.getAttribute("userId");
        blog.setUserId(userId);
        blogService.publishBlog(blog);
        return ResultUtil.success(200, "发布成功");
    }

    //删除博客
    @ApiOperation(value = "删除博客")
    @DeleteMapping("/blog")
    public ResultUtil<Object> deleteBlog(HttpServletRequest request, int blogId) {
        int userId = (int) request.getAttribute("userId");
        int status = blogService.deleteBlog(userId, blogId);
        if(status == 0) {
            return ResultUtil.error(10008, "博客不存在");
        }
        return ResultUtil.success(200, "删除成功");
    }

    //修改博客
    @ApiOperation(value = "修改博客")
    @PutMapping("/blog")
    public ResultUtil<Object> updateBlog(HttpServletRequest request, @RequestBody Blog blog) {
        int userId = (int) request.getAttribute("userId");
        blog.setUserId(userId);
        int status = blogService.updateBlog(blog);
        if(status == 0) {
            return ResultUtil.error(10008, "博客不存在");
        }
        return ResultUtil.success(200, "修改成功");
    }

    //查询某个博客详情
    @ApiOperation(value = "查询某个博客详情")
    @GetMapping("/blog")
    public ResultUtil<Object> getBlog(HttpServletRequest request, int blogId) {
        int userId = (int) request.getAttribute("userId");
        Blog blog = blogService.getBlog(userId, blogId);
        if(blog == null) {
            return ResultUtil.error(100010, "无权限");
        }
        return ResultUtil.success(200, "查询成功", blog);
    }

    //上拉加载博客
    @ApiOperation(value = "上拉加载博客")
    @PostMapping("/myBlogs")
    public ResultUtil<Object> getMyBlogList(HttpServletRequest request, @RequestBody PageDTO pageDTO) {
        int userId = (int) request.getAttribute("userId");
        pageDTO.setUserId(userId);
        List<BlogDTO> blogList = blogService.getBlogList(pageDTO);
        if(blogList.isEmpty()){
            return ResultUtil.error(10008, "没有更多博客了");
        }
        Map<String, Object> data = Map.of("blogList", blogList);
        return ResultUtil.success(200, "查询成功", data);
    }

    //查询某个用户的博客
    @ApiOperation(value = "查询某个用户的博客")
    @GetMapping("/userBlogs")
    public ResultUtil<Object> getUserBlogList(HttpServletRequest request, @RequestBody PageDTO pageDTO) {
        //权限判断
        int userId = (int) request.getAttribute("userId");
        //如果查询的不是自己的博客，需要权限判断
        if(pageDTO.getUserId() != 0) {
            int otherId = pageDTO.getUserId();
            int status = accessControlService.control(userId, otherId);
            if(status == 0) {
                return ResultUtil.error(100010, "无权限");
            }
            userId = otherId;
        }
        //分页查询
        pageDTO.setUserId(userId);
        List<BlogDTO> blogList = blogService.getBlogList(pageDTO);
        if(blogList.isEmpty()) {
            return ResultUtil.error(10008, "没有更多博客了");
        }
        return ResultUtil.success(200, "查询成功", Map.of("blogList", blogList));
    }

    //查询所有好友的博客
    @ApiOperation(value = "查询所有好友的博客")
    @PostMapping("/friendsBlogs")
    public ResultUtil<Object> getFriendsBlogList(HttpServletRequest request, @RequestBody PageDTO pageDTO) {
        //权限判断

        //分页查询
        int userId = (int) request.getAttribute("userId");
        pageDTO.setUserId(userId);
        List<BlogDTO> blogList = blogService.getFriendsBlogList(pageDTO);
        if(blogList.isEmpty()) {
            return ResultUtil.error(10008, "没有更多博客了");
        }
        Map<String, Object> data = Map.of("blogList", blogList);
        return ResultUtil.success(200, "查询成功", data);
    }
}
