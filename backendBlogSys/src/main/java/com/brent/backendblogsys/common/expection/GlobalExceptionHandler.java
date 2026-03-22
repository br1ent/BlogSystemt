package com.brent.backendblogsys.common.expection;

import com.brent.backendblogsys.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 处理所有未捕获的异常（兜底）
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 捕获所有未预料的系统异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统运行异常: ", e);
        return Result.fail("服务器冒烟了，请稍后再试");
    }
}