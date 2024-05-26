package com.example.blog.config;


import com.example.blog.interceptor.Logininterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器，拦截除了登录和注册之外的所有请求
        registry.addInterceptor(new Logininterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/updatePassword", "/sendCode");

    }

//    @Override
//    //跨域请求配置
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}