package com.example.blog.entity;

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
@Table(name = "user")
public class User{
    @Id
    @TableId(value = "user_id", type = IdType.AUTO)
    private int userId;

    private String password;

    private String email;

    private String account;

    private Date createdAt;

    private Date updatedAt;
}
