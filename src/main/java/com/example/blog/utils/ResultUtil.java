package com.example.blog.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//返回结果类
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtil<T> {
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ResultUtil<T> success(int code, String msg, T data) {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setCode(code);
        resultUtil.setMessage(msg);
        if (data != null) {
            resultUtil.setData(data);
            return resultUtil;
        }
        return resultUtil;
    }

    public static <T> ResultUtil<T> success(int code, String msg) {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setCode(code);
        resultUtil.setMessage(msg);
        return resultUtil;
    }

    public static <T> ResultUtil<T> error(int code, String message) {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setCode(code);
        resultUtil.setMessage(message);
        return resultUtil;
    }
}
