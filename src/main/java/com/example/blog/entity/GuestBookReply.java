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
@Table(name = "guest_book_reply")
public class GuestBookReply {
    @Id
    @TableId(value = "guest_book_reply_id", type = IdType.AUTO)
    private Integer guestBookReplyId;

    // 与留言表的 guestbookId 关联
    private Integer guestBookId;

    // 与用户表的 userId 关联
    private Integer sendUserId;

    // 与用户表的 userId 关联
    private Integer receiveUserId;

    private String replyContent;

    private Date replyTime;

}