package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Album;

import java.util.List;

public interface AlbumService extends IService<Album> {
    int createAlbum(Album album);
    int updateAlbum(Album album);
    List<Album> getAlbums(int userId, int otherId);
    int deleteAlbum(Album album);
}
