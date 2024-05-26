package com.example.blog.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPasswordDTO {
    private String email;
    private String password;
    private String newPassword;
}
