package com.example.blog.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDTO {
    private int userId;
    private int currentPage;
    private int pageSize;
}
