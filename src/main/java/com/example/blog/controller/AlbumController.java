package com.example.blog.controller;

import com.example.blog.entity.Album;
import com.example.blog.service.AlbumService;
import com.example.blog.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    //新建相册
    @ApiOperation(value = "新建相册")
    @PostMapping("/album")
    public ResultUtil<Object> createAlbum(HttpServletRequest request, @RequestBody Album album) {
        int userId = (int) request.getAttribute("userId");
        album.setUserId(userId);
        int status = albumService.createAlbum(album);
        if(status == 0) {
            return ResultUtil.error(10008, "参数有误");
        }
        return ResultUtil.success(200, "新建成功");
    }

    //修改相册
    @ApiOperation(value = "修改相册")
    @PutMapping("/album")
    public ResultUtil<Object> updateAlbum(HttpServletRequest request, @RequestBody Album album) {
        int userId = (int) request.getAttribute("userId");
        album.setUserId(userId);
        int status = albumService.updateAlbum(album);
        if(status == 0) {
            return ResultUtil.error(10008, "参数有误");
        }
        return ResultUtil.success(200, "修改成功");
    }

    //查询所有相册
    @ApiOperation(value = "查询所有相册")
    @GetMapping("/albums")
    public ResultUtil<Object> getAlbums(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int otherId){
        int userId = (int) request.getAttribute("userId");
        if(otherId == 0) {
            otherId = userId;
        }
        List<Album> albums = albumService.getAlbums(userId, otherId);
        Map<String, Object> data = new HashMap<>();
        data.put("albums", albums);
        if(otherId == userId) {
            data.put("isOneself", true);
        }else {
            data.put("isOneself", false);
        }
        return ResultUtil.success(200, "查询成功", data);
    }

    //删除相册
    @ApiOperation(value = "删除相册")
    @DeleteMapping("/album")
    public ResultUtil<Object> deleteAlbum(HttpServletRequest request, int albumId) {
        int userId = (int) request.getAttribute("userId");
        Album album = Album.builder().userId(userId).albumId(albumId).build();
        int status = albumService.deleteAlbum(album);
        if(status == 0) {
            return ResultUtil.error(10008, "参数有误");
        }
        return ResultUtil.success(200, "删除成功");
    }
}
