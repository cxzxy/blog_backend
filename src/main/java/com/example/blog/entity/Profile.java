package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Profile {

    @Id
    @TableId(value = "profile_id", type = IdType.AUTO)
    private Integer profileId;

    // 与用户表的 userId 关联
    private Integer userId;

    private String userName;

    private String sex;

    private Date birthday;

    private String location;

    private String signature;

    private String pictureUrl;

    private String avatarUrl;
}
