package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @TableId(value = "picture_Id", type = IdType.AUTO)
    private Integer pictureId;

    // 与相册表的 albumId 关联
    private Integer albumId;

    private String pictureUrl;

    private Date pictureCreatTime;
}