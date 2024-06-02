package com.example.blog.controller;

import com.example.blog.dto.GuestBookDTO;
import com.example.blog.entity.GuestBook;
import com.example.blog.service.GuestBookService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GuestBookController {

    @Autowired
    private GuestBookService guestBookService;

    //新建留言板
    @ApiOperation(value = "新建留言板")
    @PostMapping("/guestBook")
    public ResultUtil<Object> createGuestBook(HttpServletRequest request, @RequestBody GuestBook guestBook) {
        int sendUserId = (int) request.getAttribute("userId");
        guestBook.setSendUserId(sendUserId);
        guestBook.setCreatedAt(new Date());
        int status = guestBookService.createGuestBook(guestBook);
        if(status == 0) {
            return ResultUtil.error(10008, "参数有误");
        }
        return ResultUtil.success(200, "创建成功");
    }

    //查询所有留言板
    @ApiOperation(value = "查询所有留言板")
    @GetMapping("/guestBooks")
    public ResultUtil<Object> getGuestBooks(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int otherId ){
        int userId = (int) request.getAttribute("userId");
        if(otherId == 0) {
            otherId = userId;
        }
        List<GuestBookDTO> guestBookDTOS = guestBookService.getGuestBooks(userId, otherId);
        if(guestBookDTOS == null) {
            return ResultUtil.error(100010, "无权限或留言为空");
        }
        Map<String, Object> data = new HashMap<>();
        if(otherId == userId) {
            data.put("isOneself", true);
        }else {
            data.put("isOneself", false);
        }
        data.put("guestBookList", guestBookDTOS);
        return ResultUtil.success(200, "查询成功", data);
    }

    //删除留言板
    @ApiOperation(value = "删除留言板")
    @DeleteMapping("/guestBook")
    public ResultUtil<Object> deleteGuestBook(HttpServletRequest request, @RequestParam int guestBookId) {
        int userId = (int) request.getAttribute("userId");
        int status = guestBookService.deleteGuestBook(userId, guestBookId);
        if(status == 0) {
            return ResultUtil.error(10009, "参数有误");
        }else return ResultUtil.success(200, "删除成功");
    }
}
