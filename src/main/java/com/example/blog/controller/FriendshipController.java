package com.example.blog.controller;

import com.example.blog.dto.FriendDTO;
import com.example.blog.dto.FriendRecommandDTO;
import com.example.blog.entity.Friendship;
import com.example.blog.service.FriendshipService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    //查询推荐好友
    @ApiOperation(value = "查询推荐好友")
    @GetMapping("/friendRecommands")
    public ResultUtil<Object> selectFriendsByUserId(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        List<FriendRecommandDTO> friends = friendshipService.selectFriendsByUserId(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("friendRecommandList", friends);
        return ResultUtil.success(200, "查询成功", data);
    }

    //查询所有好友
    @ApiOperation(value = "查询所有好友")
    @GetMapping("/friends")
    public ResultUtil<Object> selectAllFriends(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        List<FriendDTO> friends = friendshipService.selectAllFriends(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("friendList", friends);
        return ResultUtil.success(200, "查询成功", data);
    }

    //删除好友
    @ApiOperation(value = "删除好友")
    @DeleteMapping("/friend")
    public ResultUtil<Object> deleteFriend(HttpServletRequest request, int friendId) {
        int userId = (int) request.getAttribute("userId");
        friendshipService.deleteFriend(userId, friendId);
        return ResultUtil.success(200, "删除成功");
    }

    //修改好友备注
    @ApiOperation(value = "修改好友备注")
    @PutMapping("/friend/remark")
    public ResultUtil<Object> updateFriendRemark(HttpServletRequest request, @RequestBody Friendship friendship){
        int userId = (int) request.getAttribute("userId");
        friendship.setUserId(userId);
        friendshipService.updateFriendRemark(friendship);
        return ResultUtil.success(200, "修改成功");
    }
}
