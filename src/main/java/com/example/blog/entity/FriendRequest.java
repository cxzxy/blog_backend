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
@Table(name = "friend_request")
public class FriendRequest {

    @Id
    @TableId(value = "request_id", type = IdType.AUTO)
    private int requestId;

    // 与用户表的 userId 关联
    private int userId;

    // 与用户表的 userId 关联
    private int friendId;

    private String status;

    private Date createdAt;
}