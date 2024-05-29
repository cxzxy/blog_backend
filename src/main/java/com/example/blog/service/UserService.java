package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.UserPasswordDTO;
import com.example.blog.dto.UserRegisterDTO;
import com.example.blog.entity.User;

public interface UserService extends IService<User> {

    String register(UserRegisterDTO user);

    int login(User user);

    boolean logout(int userId);

    int updatePassword(UserPasswordDTO userPasswordDTO);

    int sendCode(String email);
}
