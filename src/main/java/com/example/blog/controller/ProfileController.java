package com.example.blog.controller;

import com.example.blog.dto.UserProfileDTO;
import com.example.blog.entity.Profile;
import com.example.blog.service.ProfileService;
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
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    //查询用户信息
    @ApiOperation(value = "查询用户信息")
    @GetMapping("/profile")
    public ResultUtil<Object> getProfile(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int otherId) {
        int userId = (int) request.getAttribute("userId");
        if(otherId == 0) {
            otherId = userId;
        }
        UserProfileDTO profile = profileService.getProfileByUserId(userId, otherId);
        if(profile == null) {
            return ResultUtil.error(10001, "用户不存在");
        }
        Map<String, Object> data = Map.of("userProfile", profile);
        return ResultUtil.success(200, "查询成功", data);
    }

    //修改用户信息
    @ApiOperation(value = "修改用户信息")
    @PutMapping("/profile")
    public ResultUtil<Object> updateProfile(@RequestBody Profile profile) {
        profile = profileService.updateProfile(profile);
        if(profile == null) {
            return ResultUtil.error(10001, "用户不存在");
        } else {
            return ResultUtil.success(200, "修改成功");
        }
    }

    //上传头像
    @ApiOperation(value = "上传头像")
    @PostMapping("/uploadAvatar")
    public ResultUtil<Object> uploadAvatar(HttpServletRequest request, MultipartFile file) {
        int userId = (int) request.getAttribute("userId");
        //上传头像
        String fileUrl = OSSUtil.uploadImg(file);
        //更新用户头像
        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setAvatarUrl(fileUrl);
        profileService.updateAvatarUrl(profile);
        Map<String, String> data = new HashMap<>();
        data.put("avatarUrl", fileUrl);
        return ResultUtil.success(200, "上传成功", data);
    }

    //根据账户查询用户信息
    @ApiOperation(value = "根据账户查询用户信息")
    @GetMapping("/profileByAccount")
    public ResultUtil<Object> getProfileByAccount(HttpServletRequest request, @RequestParam String account) {
        int userId = (int) request.getAttribute("userId");
        UserProfileDTO userProfileDTO = profileService.getProfileByAccount(userId, account);
        if(userProfileDTO == null) {
            return ResultUtil.error(10001, "用户不存在");
        }
        Map<String, Object> data = Map.of("userProfile", userProfileDTO);
        return ResultUtil.success(200, "查询成功", data);
    }
}
