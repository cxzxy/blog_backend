package com.example.blog.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendDTO {
    private int userId;
    private String userName;
    private String avatarUrl;
    private String signature;
}
