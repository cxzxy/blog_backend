package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.SpaceDecoration;

public interface SpaceDecorationService extends IService<SpaceDecoration> {
    SpaceDecoration getSpaceDecoration(int userId);
    int updateSpaceDecoration(SpaceDecoration spaceDecoration);
}
