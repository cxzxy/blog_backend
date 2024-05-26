package com.example.blog.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //加密
    public static String encode(String password) {
        return encoder.encode(password);
    }

    //匹配
    public static boolean matches(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }

}
