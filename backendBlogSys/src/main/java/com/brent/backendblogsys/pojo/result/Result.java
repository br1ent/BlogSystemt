package com.brent.backendblogsys.pojo.result;

import lombok.Data;

// 统一返回结果类，泛型T用于接收任意类型的返回数据
@Data
public class Result<T> {
    // 状态码：成功一般用200，失败用非200（如500、400等）
    private int code;
    private String msg;
    private T data;

    private Result() {}

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // -------------------------- 成功返回的静态方法 --------------------------
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    // -------------------------- 失败返回的静态方法 --------------------------
    public static <T> Result<T> fail() {
        return new Result<>(500, "操作失败", null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}