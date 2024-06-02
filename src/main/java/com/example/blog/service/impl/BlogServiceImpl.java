package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.BlogDTO;
import com.example.blog.dto.PageDTO;
import com.example.blog.entity.Blog;
import com.example.blog.mapper.BlogMapper;
import com.example.blog.mapper.FriendshipMapper;
import com.example.blog.service.AccessControlService;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Autowired
    private AccessControlService accessControlService;

    @Override
    public void publishBlog(Blog blog) {
        blog.setCreatedAt(new Date());
        blog.setUpdatedAt(new Date());
        blogMapper.insert(blog);
    }

    @Override
    public int deleteBlog(int userId, int blogId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("blog_id", blogId);
        if(blogMapper.selectOne(queryWrapper) == null){
            return 0;
        }
        blogMapper.delete(queryWrapper);
        return 1;
    }

    @Override
    public int updateBlog(Blog blog) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", blog.getUserId());
        queryWrapper.eq("blog_id", blog.getBlogId());
        if(blogMapper.selectOne(queryWrapper) == null){
            return 0;
        }
        blog.setUpdatedAt(new Date());
        blogMapper.updateById(blog);
        return 1;
    }

    @Override
    public Blog getBlog(int userId, int blogId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        Blog blog = blogMapper.selectOne(queryWrapper);
        if(blog == null){
            return null;
        }
        if(blog.getUserId() == userId){
            return blog;
        }else{
            int otherId = blog.getUserId();
            int status = accessControlService.control(userId, otherId);
            if(status == 1){
                return blog;
            }else{
                return null;
            }
        }
    }

    @Override
    public List<BlogDTO> getBlogList(PageDTO pageDTO) {
        //根据userId分页查询自己的博客，按照createdAt降序排列
        pageDTO.setCurrentPage((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        return blogMapper.BlogList(pageDTO);
    }

    @Override
    public List<BlogDTO> getFriendsBlogList(PageDTO pageDTO) {
        List<Integer> list = friendshipMapper.selectFriendsByUserId(pageDTO.getUserId());
        //将自己的userId加入
        list.add(pageDTO.getUserId());
        List<BlogDTO> blogList = blogMapper.BlogListByUserIds((pageDTO.getCurrentPage()-1) * pageDTO.getPageSize(), pageDTO.getPageSize(), list);
        //遍历判断是否有权限查看
        for(int i = 0; i < blogList.size(); i++){
            BlogDTO blog = blogList.get(i);
            int status = accessControlService.control(pageDTO.getUserId(), blog.getUserId());
            if(status == 0){
                blogList.remove(i);
                i--;
            }
        }
        return blogList;
    }
}
