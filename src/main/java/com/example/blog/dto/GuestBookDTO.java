package com.example.blog.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestBookDTO {
    private int guestBookId;
    private int sendUserId;
    private String sendUserName;
    private String sendUserAvatarUrl;
    private String guestBookContent;
    private String createdAt;
}
