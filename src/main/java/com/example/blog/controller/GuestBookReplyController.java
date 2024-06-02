package com.example.blog.controller;

import com.example.blog.dto.GuestBookDataDTO;
import com.example.blog.entity.GuestBookReply;
import com.example.blog.service.GuestBookReplyService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class GuestBookReplyController {
    @Autowired
    private GuestBookReplyService guestBookReplyService;

    //查询所有留言回复
    @ApiOperation(value = "查询留言详情")
    @GetMapping("/guestBookReply")
    public ResultUtil<Object> getGuestBookReply(HttpServletRequest request, int guestBookId) {
        int userId = (int) request.getAttribute("userId");
        GuestBookDataDTO data = guestBookReplyService.getGuestBookReply(guestBookId, userId);
        if (data == null) return ResultUtil.error(10009, "无权限或参数有误");
        return ResultUtil.success(200, "查询成功", data);
    }

    //回复留言
    @ApiOperation(value = "回复留言")
    @PostMapping("/guestBookReply")
    public ResultUtil<Object> createGuestBookReply(HttpServletRequest request, @RequestBody GuestBookReply guestBookReply) {
        int userId = (int) request.getAttribute("userId");
        guestBookReply.setSendUserId(userId);
        guestBookReply.setReplyTime(new Date());
        System.out.println("time+ " + guestBookReply.getReplyTime());
        int status = guestBookReplyService.createGuestBookReply(guestBookReply);
        if (status == 0) {
            return ResultUtil.error(100010, "无权限或参数有误");
        }
        return ResultUtil.success(200, "回复成功");
    }

    //删除留言回复
    @ApiOperation(value = "删除留言回复")
    @DeleteMapping("/guestBookReply")
    public ResultUtil<Object> deleteGuestBookReply(HttpServletRequest request, @RequestParam int guestBookReplyId) {
        int userId = (int) request.getAttribute("userId");
        int status = guestBookReplyService.deleteGuestBookReply(userId, guestBookReplyId);
        if (status == 0) {
            return ResultUtil.error(10009, "参数有误");
        }
        return ResultUtil.success(200, "删除成功");
    }
}
