package com.pace2car.springbootdemo.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pace2Car
 * @date 2019/1/11 13:59
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LogManager.getLogger("globalExceptionHandler");

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorInfo jsonErrorHandler(HttpServletRequest req, Exception e) {
        logger.warn(e.getMessage()+" : "+req.getRequestURL().toString());
        ErrorInfo r = new ErrorInfo();
        if (e instanceof UnauthorizedException) {
            logger.info("用户无权访问 : " + req.getRequestURL().toString());
            r.setMessage(e.getMessage())
                    .setCode("403")
                    .setData("无权访问")
                    .setUrl(req.getRequestURL().toString());
            return r;
        }
        r.setMessage(e.getMessage())
                .setCode("500")
                .setData("系统故障，请与管理员联系@chenjiahao")
                .setUrl(req.getRequestURL().toString());
        return r;
    }
}
