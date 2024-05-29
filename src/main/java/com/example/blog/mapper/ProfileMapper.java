package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dto.UserProfileDTO;
import com.example.blog.entity.Profile;

public interface ProfileMapper extends BaseMapper<Profile> {
    UserProfileDTO getProfileByAccount(String account);
    UserProfileDTO getProfileByUserId(int otherId);
}
