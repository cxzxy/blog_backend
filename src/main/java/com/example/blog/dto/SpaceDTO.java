package com.example.blog.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpaceDTO {
    int userId;
    String userName;
    String avatarUrl;
    int blogNum;
    int albumNum;
    int guestBookNum;
}
