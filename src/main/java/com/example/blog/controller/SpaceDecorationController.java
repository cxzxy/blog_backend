package com.example.blog.controller;

import com.example.blog.entity.SpaceDecoration;
import com.example.blog.service.SpaceDecorationService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class SpaceDecorationController {
    @Autowired
    private SpaceDecorationService spaceDecorationService;

    //查询空间装扮
    @ApiOperation(value = "查询空间装扮")
    @GetMapping("/spaceDecoration")
    public ResultUtil<Object> getSpaceDecoration(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        SpaceDecoration spaceDecoration = spaceDecorationService.getSpaceDecoration(userId);
        if (spaceDecoration == null) {
            return ResultUtil.error(10009, "参数有误");
        } else {
            return ResultUtil.success(200, "查询成功", Map.of("spaceDecoration", spaceDecoration));
        }
    }

    //修改空间装扮
    @ApiOperation(value = "修改空间装扮")
    @PutMapping("/spaceDecoration")
    public ResultUtil<Object> updateSpaceDecoration(HttpServletRequest request, @RequestBody SpaceDecoration spaceDecoration) {
        int userId = (int) request.getAttribute("userId");
        spaceDecoration.setUserId(userId);
        int status = spaceDecorationService.updateSpaceDecoration(spaceDecoration);
        if (status == 0) {
            return ResultUtil.error(10009, "参数有误");
        } else {
            return ResultUtil.success(200, "修改成功");
        }
    }
}
