package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Album;
import com.example.blog.entity.Picture;
import com.example.blog.mapper.AlbumMapper;
import com.example.blog.mapper.PictureMapper;
import com.example.blog.service.AccessControlService;
import com.example.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AccessControlService accessControlService;

    @Override
    public int uploadPicture(Picture picture) {
        picture.setPictureCreatTime(new Date());
        return pictureMapper.insert(picture);
    }

    @Override
    public int deletePicture(int userId, int albumId, int pictureId) {
        //先判断相册是否属于该用户
        Album album = albumMapper.selectById(albumId);
        if (album == null||(album.getUserId() != userId)) {
            return 0;
        }
        //再判断图片是否属于该相册
        Picture picture = pictureMapper.selectById(pictureId);
        if (picture == null||(picture.getAlbumId() != albumId))  {
            return 0;
        }
        return pictureMapper.deleteById(pictureId);
    }

    @Override
    public List<Picture> getPictures(int userId, int albumId, int otherId) {
        //先判断相册是否属于该用户
        Album album = albumMapper.selectById(albumId);
        if (album == null||(album.getUserId() != otherId)) {
            return null;
        }
        if(userId != otherId){
            //查看其他用户相册时，权限判断
            int status = accessControlService.control(userId, otherId);
            if (status != 1) {
                return null;
            }
        }
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("album_id", albumId);
        return pictureMapper.selectList(queryWrapper);
    }

}
