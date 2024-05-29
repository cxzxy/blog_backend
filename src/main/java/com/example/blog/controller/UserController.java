package com.example.blog.controller;

import com.example.blog.dto.UserPasswordDTO;
import com.example.blog.dto.UserRegisterDTO;
import com.example.blog.entity.Profile;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import com.example.blog.utils.JWTUtil;
import com.example.blog.utils.OSSUtil;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //实现用户登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResultUtil<Object> login(@RequestBody User user) {
        int status = userService.login(user);
        if (status == -1) {
            return ResultUtil.error(10001, "用户不存在");
        } else if(status == -2){
            return ResultUtil.error(10002, "密码错误");
        }else {
            user.setUserId(status);
            String token = JWTUtil.createToken(user);
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            return ResultUtil.success(200, "登录成功", data);
        }
    }

    //实现用户注册
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ResultUtil<Object> register(@RequestBody UserRegisterDTO user) {
        String status = userService.register(user);
        if(status.equals("-1")){
            return ResultUtil.error(10003, "验证码错误");
        }else if(status.equals("-2")){
            return ResultUtil.error(10001, "用户已存在");
        }else {
            Map<String, String> data = Map.of("account", status);
            return ResultUtil.success(200, "注册成功", data);
        }
    }

    //实现用户注销
    @ApiOperation(value = "用户注销")
    @DeleteMapping("/cancel")
    public ResultUtil<Object> logout(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        boolean result = userService.logout(userId);
        if(result){
            return ResultUtil.success(200, "注销成功");
        }else {
            return ResultUtil.error(10001, "用户不存在");
        }
    }

    //修改密码
    @ApiOperation(value = "修改密码")
    @PutMapping("/updatePassword")
    public ResultUtil<Object> updatePassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        int status = userService.updatePassword(userPasswordDTO);
        if(status == 0){
            return ResultUtil.success(200, "修改成功");
        }else if(status == -1){
            return ResultUtil.error(10001, "用户不存在");
        }else{
            return ResultUtil.error(10002, "密码错误");
        }
    }

    //发送验证码
    @ApiOperation(value = "发送验证码")
    @GetMapping("/sendCode")
    public ResultUtil<Object> sendCode(@RequestParam String email) {
        int status = userService.sendCode(email);
        return ResultUtil.success(200, "发送成功");
    }
}
