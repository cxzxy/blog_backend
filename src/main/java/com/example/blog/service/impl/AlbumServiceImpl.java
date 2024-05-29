package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.entity.Album;
import com.example.blog.mapper.AlbumMapper;
import com.example.blog.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public int createAlbum(Album album) {
        album.setAlbumCreatTime(new Date());
        return albumMapper.insert(album);
    }

    @Override
    public int updateAlbum(Album album) {
        return albumMapper.updateById(album);
    }

    @Override
    public List<Album> getAlbums(int userId, int otherId) {
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", otherId);
        return albumMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteAlbum(Album album) {
        //先判断相册是否属于该用户
        Album album1 = albumMapper.selectById(album.getAlbumId());
        System.out.println("userId:"+album.getUserId());
        if (album1 == null||(!album1.getUserId().equals(album.getUserId()))) {
            return 0;
        }
        return albumMapper.deleteById(album.getAlbumId());
    }
}
