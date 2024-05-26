package com.example.blog.interceptor;

import com.example.blog.utils.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class Logininterceptor implements HandlerInterceptor{
    @Override
    //在请求处理之前进行调用（Controller方法调用之前）
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("Authorization");
        int code = JWTUtil.tokenToOut(token);
        if (code < 0) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter pw = response.getWriter();
            pw.write("{\"code\":10002,\"message\":\"令牌过期或不合法\"}");
            pw.flush();
            pw.close();
            return false;
        }else{
            //将用户id存入request
            request.setAttribute("userId", code);
            return true;
        }

    }

        //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
    }
    @Override
    //在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
