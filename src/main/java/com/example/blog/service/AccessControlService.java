package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.dto.SpaceDTO;
import com.example.blog.entity.AccessControl;

public interface AccessControlService extends IService<AccessControl> {
    AccessControl getAccessControl(int userId);
    int updateAccessControl(AccessControl accessControl);
    SpaceDTO getSpace(int userId, int otherId);
    int control(int userId, int otherId);
}
