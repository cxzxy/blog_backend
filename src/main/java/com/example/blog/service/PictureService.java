package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Picture;

import java.util.List;

public interface PictureService extends IService<Picture> {
    int uploadPicture(Picture picture);
    int deletePicture(int userId, int albumId, int pictureId);
    List<Picture> getPictures(int userId, int albumId, int otherId);
}
