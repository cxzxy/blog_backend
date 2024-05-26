package com.example.blog.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "album")
public class Album {

    @Id
    @TableId(value = "albums_id", type = IdType.AUTO)
    private Integer albumsId;

    // 与用户表的 userId 关联
    private Integer userId;

    private String albumsTitle;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date albumsCreatTime;
}