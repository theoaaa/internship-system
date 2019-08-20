package org.scau.internshipsystem.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.scau.internshipsystem.common.domain.ExceptionResult;
import org.scau.internshipsystem.common.exception.SysException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 控制层统一异常处理
 * **/
@RestControllerAdvice
@Slf4j
public class MvcExceptionHandler {

    @ExceptionHandler(SysException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResult sysExceptionHandler(SysException e){
        log.error("控制层系统异常，异常信息：" + e.getMessage());
        return ExceptionResult.builder()
                .code(500)
                .msg("系统异常")
                .result(false)
                .build();
    }
}
