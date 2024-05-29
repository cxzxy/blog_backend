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
@Table(name = "guestbook")
public class GuestBook {
    @Id
    @TableId(value = "guestbook_id", type = IdType.AUTO)
    private Integer guestbookId;

    // 与用户表的 userId 关联
    private Integer userId;

    // 与用户表的 userId 关联
    private Integer sendUserId;

    private String guestbookContent;

    private Date createdAt;
}