package com.example.blog.controller;

import com.example.blog.entity.FriendRequest;
import com.example.blog.service.FriendRequestService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;


    @ApiOperation(value = "请求添加好友")
    @PostMapping("/friend/addtru")
    public ResultUtil<Object> addFriend(HttpServletRequest request, int friendId) {
        int userId = (int) request.getAttribute("userId");
        //请求添加好友
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUserId(userId);
        friendRequest.setFriendId(friendId);
        int status = friendRequestService.addRequest(friendRequest);
        if(status == -1) {
            //用户不存在
            return ResultUtil.error(10001, "用户不存在");
        } else if(status == 0) {
            //已经是好友
            return ResultUtil.error(10006, "已经是好友");
        } else if(status == 1) {
            //已经发送过请求
            return ResultUtil.error(10007, "已经发送过请求");
        } else {
            //发送请求成功
            return ResultUtil.success(200, "发送请求成功");
        }
    }

    @ApiOperation(value = "回复好友请求")
    @PutMapping("/friend/reply")
    public ResultUtil<Object> replyFriendRequest(HttpServletRequest request, int friendId, boolean reply) {
        //回复好友请求
        int userId = (int) request.getAttribute("userId");
        int status = friendRequestService.replyFriendRequest(userId, friendId, reply);
        if(status == -1) {
            //用户不存在
            return ResultUtil.error(10001, "用户不存在");
        }
        return ResultUtil.error(200, "添加好友成功");

    }

}
