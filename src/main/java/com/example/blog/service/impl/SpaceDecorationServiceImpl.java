package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.SpaceDecoration;
import com.example.blog.mapper.SpaceDecorationMapper;
import com.example.blog.service.SpaceDecorationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SpaceDecorationServiceImpl extends ServiceImpl<SpaceDecorationMapper, SpaceDecoration> implements SpaceDecorationService {

    @Autowired
    private SpaceDecorationMapper spaceDecorationMapper;

    @Override
    public SpaceDecoration getSpaceDecoration(int userId){
        QueryWrapper<SpaceDecoration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return getOne(queryWrapper);
    }

    @Override
    public int updateSpaceDecoration(SpaceDecoration spaceDecoration){
        int userId = spaceDecoration.getUserId();
        String theme = spaceDecoration.getThemeIndex();
        spaceDecoration = getSpaceDecoration(userId);
        if(spaceDecoration == null) return 0;
        spaceDecoration.setThemeIndex(theme);
        spaceDecoration.setUpdatedAt(new Date());
        return spaceDecorationMapper.updateById(spaceDecoration);
    }
}
