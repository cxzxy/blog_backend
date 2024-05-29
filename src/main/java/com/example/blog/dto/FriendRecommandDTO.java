package com.example.blog.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendRecommandDTO {
    private int userId;
    private String userName;
    private String avatarUrl;
    private String sex;
    private Date birthday;
}
