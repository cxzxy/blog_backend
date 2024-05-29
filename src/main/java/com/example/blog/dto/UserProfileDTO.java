package com.example.blog.dto;

import lombok.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileDTO {
    private int userId;
    private String email;
    private String account;
    private Integer profileId;
    private String userName;
    private String sex;
    private Date birthday;
    private String location;
    private String signature;
    private String pictureUrl;
    private String avatarUrl;
    private Boolean isFriend;
    private String remarks;
    private Boolean isOneself;
}
