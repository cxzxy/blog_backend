package com.example.blog.controller;

import com.example.blog.dto.SpaceDTO;
import com.example.blog.entity.AccessControl;
import com.example.blog.service.AccessControlService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccessControlController {
    @Autowired
    private AccessControlService accessControlService;

    //查询权限
    @ApiOperation(value = "查询权限")
    @GetMapping("/accessControl")
    public ResultUtil<Object> getAccessControl(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        AccessControl accessControl = accessControlService.getAccessControl(userId);
        Map<String, Object> data = Map.of("accessControl", accessControl);
        return ResultUtil.success(200, "查询成功", data);
    }

    //修改权限
    @ApiOperation(value = "修改权限")
    @PutMapping("/accessControl")
    public ResultUtil<Object> updateAccessControl(HttpServletRequest request, @RequestBody AccessControl accessControl) {
        int userId = (int) request.getAttribute("userId");
        accessControl.setUserId(userId);
        int status = accessControlService.updateAccessControl(accessControl);
        if(status == 0) {
            return ResultUtil.error(10008, "参数有误");
        }
        return ResultUtil.success(200, "修改成功");
    }

    //空间展示
    @ApiOperation(value = "空间展示")
    @GetMapping("/space")
    public ResultUtil<Object> getSpace(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int otherId) {
    int userId = (int) request.getAttribute("userId");
        if(otherId == 0) {
            otherId = userId;
        }
        SpaceDTO spaceDTO = accessControlService.getSpace(userId, otherId);
        Map<String, Object> data = new HashMap<>();
        if(otherId == userId) {
            data.put("isOneself", true);
        }else {
            data.put("isOneself", false);
        }
        data.put("spaceInformation", spaceDTO);
        return ResultUtil.success(200, "查询成功", data);
    }

    //进入空间权限判断
    @ApiOperation(value = "进入空间权限判断")
    @GetMapping("/enterSpace")
    public ResultUtil<Object> judgeSpace(HttpServletRequest request,  @RequestParam(required = false, defaultValue = "0")int otherId) {
        int userId = (int) request.getAttribute("userId");
        if(otherId == 0) {
            return ResultUtil.success(200, "可以进入");
        }
        int status = accessControlService.control(userId, otherId);
        if(status == 1) {
            return ResultUtil.success(200, "可以进入");
        }else if(status == 0) {
            return ResultUtil.error(10010, "无权限");
        }else if(status == -1) {
            return ResultUtil.error(10009, "空间不存在");
        }else {
            return ResultUtil.error(10009, "参数有误");
        }
    }

}
