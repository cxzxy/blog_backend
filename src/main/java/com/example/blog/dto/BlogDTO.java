package com.example.blog.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogDTO {
    private int userId;
    private int blogId;
    private String userName;
    private String title;
    private String blogContent;
    private String createdAt;
    private String updatedAt;
    private String avatarUrl;
}
