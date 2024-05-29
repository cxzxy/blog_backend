package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.UserProfileDTO;
import com.example.blog.entity.Profile;

public interface ProfileService extends IService<Profile> {
    UserProfileDTO getProfileByUserId(int userId, int otherId);
    Profile updateProfile(Profile profile);
    void updateAvatarUrl(Profile profile);
    UserProfileDTO getProfileByAccount(int userId, String account);
}
