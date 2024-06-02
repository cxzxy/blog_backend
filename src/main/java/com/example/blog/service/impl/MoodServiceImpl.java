package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Mood;
import com.example.blog.mapper.MoodMapper;
import com.example.blog.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MoodServiceImpl extends ServiceImpl<MoodMapper, Mood> implements MoodService {

    @Autowired
    private MoodMapper moodMapper;

    @Override
    public Mood getMood(int userId){
        QueryWrapper<Mood> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return getOne(queryWrapper);
    }

    @Override
    public int updateMood(Mood mood){
        String moodContent = mood.getMoodContent();
        System.out.println("moodContent" + moodContent);
        mood = getMood(mood.getUserId());
        if(mood == null) return 0;
        mood.setMoodContent(moodContent);
        mood.setUpdatedAt(new Date());
        return moodMapper.updateById(mood);
    }
}
