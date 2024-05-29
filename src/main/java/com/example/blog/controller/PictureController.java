package com.example.blog.controller;

import com.example.blog.entity.Picture;
import com.example.blog.service.PictureService;
import com.example.blog.utils.OSSUtil;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    //上传图片到相册
    @ApiOperation(value = "上传图片")
    @PostMapping("/picture")
    public ResultUtil<Object> uploadPicture(HttpServletRequest request, MultipartFile file, int albumId) {
        String pictureUrl = OSSUtil.uploadImg(file);
        Picture picture = Picture.builder().albumId(albumId).pictureUrl(pictureUrl).build();
        int status = pictureService.uploadPicture(picture);
        if (status == 1) {
            return ResultUtil.success(200, "上传成功");
        } else {
            return ResultUtil.error(10009, "参数有误");
        }
    }

    //删除图片
    @ApiOperation(value = "删除图片")
    @DeleteMapping("/picture")
    public ResultUtil<Object> deletePicture(HttpServletRequest request, int albumId, int pictureId) {
        int userId = (int) request.getAttribute("userId");
        int status = pictureService.deletePicture(userId, albumId, pictureId);
        if (status == 1) {
            return ResultUtil.success(200, "删除成功");
        } else {
            return ResultUtil.error(10009, "参数有误");
        }
    }

    //查询相册下的所有图片
    @ApiOperation(value = "查询图片")
    @GetMapping("/pictures")
    public ResultUtil<Object> getPictures(HttpServletRequest request, int albumId, @RequestParam(required = false, defaultValue = "0") int otherId) {
        int userId = (int) request.getAttribute("userId");
        if(otherId == 0) {
            otherId = userId;
        }
        List<Picture> pictures = pictureService.getPictures(userId, albumId, otherId);
        if (pictures == null) {
            return ResultUtil.error(10009, "参数有误");
        } else {
            Map<String, Object> data = new HashMap<>();
            if (otherId == userId) {
                data.put("isOneself", true);
            } else {
                data.put("isOneself", false);
            }
            data.put("pictures", pictures);
            return ResultUtil.success(200, "查询成功", data);
        }
    }
}
