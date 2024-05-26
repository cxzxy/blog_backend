package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Profile;
import com.example.blog.mapper.ProfileMapper;
import com.example.blog.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile> implements ProfileService{

    @Autowired
    private ProfileMapper profileMapper;

    /**
     * 获取用户档案
     * @param userId 用户ID
     * @return 用户档案
     */
    @Override
    public Profile getProfileByUserId(int userId) {
        QueryWrapper<Profile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return profileMapper.selectOne(queryWrapper);
    }

    /**
     * 修改用户档案
     * @param profile 用户档案
     * @return 修改后的用户档案
     */
    @Override
    public Profile updateProfile(Profile profile) {
        profileMapper.updateById(profile);
        return profile;
    }

    /**
     * 更新用户头像
     * @param profile 用户档案
     */
    @Override
    public void updateAvatarUrl(Profile profile) {
        UpdateWrapper<Profile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", profile.getUserId());
        Profile user = new Profile();
        user.setAvatarUrl(profile.getAvatarUrl());
        this.update(user, updateWrapper);
    }
}
