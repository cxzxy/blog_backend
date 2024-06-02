package com.example.blog.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestBookReplyDTO {
    private int guestBookReplyId;
    private int sendUserId;
    private String sendUserName;
    private String sendUserAvatarUrl;
    private String replyUserName;
    private Boolean isToOwner;
    private String replyContent;
    private Date replyTime;
}
