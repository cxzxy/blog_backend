package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Mood;

public interface MoodService extends IService<Mood> {
    Mood getMood(int userId);
    int updateMood(Mood mood);
}
