package com.lucky.admin.platform.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestControllerAdvice
public class RestControllerExceptionHandleAdvice {

    private final static Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandleAdvice.class);

    @ExceptionHandler(value = java.lang.Exception.class)
    public ApiResult handler(HttpServletRequest req, HttpServletResponse res, Exception e) {

        logger.info("Restful Http请求发生异常...");

        if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            logger.info("修改返回状态值为200");
            res.setStatus(HttpStatus.OK.value());
        }

        if (e instanceof BizException) {
            logger.error("代码00：发生业务异常" + e.getMessage(), e);
            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("代码00：发生业务异常").build();
        } else if (e instanceof NullPointerException) {
            logger.error("代码01：" + e.getMessage(), e);
            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("代码01：发生空指针异常").build();
        } else if (e instanceof IllegalArgumentException) {
            logger.error("代码02：" + e.getMessage(), e);
            return  ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("代码02：请求参数类型不匹配").build();
        } else if (e instanceof SQLException) {
            logger.error("代码03：" + e.getMessage(), e);
            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("代码03：数据库访问异常").build();
        } else {
            logger.error("代码99：" + e.getMessage(), e);
            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("代码99：服务器代码发生异常,请联系管理员").build();
        }
    }
}
