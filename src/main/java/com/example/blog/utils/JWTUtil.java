package com.example.blog.utils;

import com.example.blog.entity.User;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    public static String  createToken(User user) {
        JwtBuilder jwtBuilder =  Jwts.builder()
                .setId(user.getUserId()+"")
                .setSubject(user.getAccount())    //用户邮箱
                .setIssuedAt(new Date())        //登录时间
                .signWith(SignatureAlgorithm.HS256, "my-123").setExpiration(new Date(new
                        Date().getTime()+86400000));
        //设置过期时间
        //前三个为载荷playload 最后一个为头部 header
        return  jwtBuilder.compact();
    }

    public static int tokenToOut(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("my-123")
                    .parseClaimsJws(token)
                    .getBody();
            int userId = Integer.parseInt(claims.getId());
            return userId;
        } catch (SignatureException e) {
            // 令牌签名不正确
            return -1;
        } catch (MalformedJwtException e) {
            // 令牌格式不正确
            return -2;
        } catch (ExpiredJwtException e) {
            // 令牌已过期
            return -3;
        } catch (UnsupportedJwtException e) {
            // 不支持的JWT
            return -4; // 定义状态码，例如404表示不支持
        } catch (IllegalArgumentException e) {
            // JWT字符串为空
            return -5; // 定义状态码，例如405表示无效令牌
        }
    }
}
