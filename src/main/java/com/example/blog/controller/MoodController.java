package com.example.blog.controller;

import com.example.blog.entity.Mood;
import com.example.blog.service.MoodService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class MoodController {
    @Autowired
    private MoodService moodService;

    //查询自己心情
    @ApiOperation(value = "查询自己心情")
    @GetMapping("/mood")
    public ResultUtil<Object> getMood(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Mood mood = moodService.getMood(userId);
        if (mood == null) return ResultUtil.error(10009, "参数有误");
        return ResultUtil.success(200, "查询成功", Map.of("mood", mood));
    }

    //修改自己心情
    @ApiOperation(value = "修改自己心情")
    @PutMapping("/mood")
    public ResultUtil<Object> updateMood(HttpServletRequest request, @RequestBody Mood mood) {
        int userId = (int) request.getAttribute("userId");
        mood.setUserId(userId);
        int status = moodService.updateMood(mood);
        if (status == 0) {
            return ResultUtil.error(10009, "参数有误");
        }else {
            return ResultUtil.success(200, "修改成功");
        }
    }
}
