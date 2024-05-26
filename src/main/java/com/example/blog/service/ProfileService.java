package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Profile;

public interface ProfileService extends IService<Profile> {
    Profile getProfileByUserId(int userId);
    Profile updateProfile(Profile profile);
    void updateAvatarUrl(Profile profile);
}
