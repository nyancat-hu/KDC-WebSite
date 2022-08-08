/*
 * @Author: Wonder2019 
 * @Date: 2020-04-30 23:16:38 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-08 14:34:47
 */
package top.imwonder.mcauth.controller.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import top.imwonder.mcauth.exception.WonderMcException;
import top.imwonder.mcauth.exception.WonderMcException.ErrorBody;

@ControllerAdvice
public class GloubleExceptionHandler implements AccessDeniedHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ErrorBody exceptionHandler(HttpServletResponse res, Exception ex) {
        res.setStatus(500);
        String msg = ex.getMessage();
        log.warn(msg, ex);
        msg = msg != null && msg.indexOf("SQL") > -1 ? "服务器异常，请联系管理员" : msg;
        WonderMcException exception = WonderMcException.otherWonderMcException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        return exception.getErrorBody();
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public ErrorBody nullPointerExceptionHandler(HttpServletResponse res, NullPointerException ex) {
        res.setStatus(500);
        WonderMcException exception = WonderMcException.otherWonderMcException(HttpStatus.NOT_FOUND, "信息已过期，请重新登录！(或未注册)");
        return exception.getErrorBody();
    }
    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorBody noHandlerFoundExceptionHandler(HttpServletResponse res, NoHandlerFoundException ex) {
        res.setStatus(404);
        WonderMcException exception = WonderMcException.otherWonderMcException(HttpStatus.NOT_FOUND, "您请求的资源未找到！");
        return exception.getErrorBody();
    }

    @ResponseBody
    @ExceptionHandler(WonderMcException.class)
    public ErrorBody wonderResourceNotFoundExceptionHandler(HttpServletResponse res, WonderMcException ex) {
        res.setStatus(ex.getStatus().value());
        return ex.getErrorBody();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String authorizationExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model,
            Exception ex) {
        res.setStatus(403);
        log.info("in 403!");
        model.addAttribute("code", 403);
        model.addAttribute("msg", "非法访问，您无权限执行此操作");
        return checkRequestType(req.getRequestURI(), req);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex)
            throws IOException, ServletException {
        log.info("in hand!");
        req.setAttribute("msg", "非法访问，您无权限执行此操作");
        req.setAttribute("code", 403);
        req.getRequestDispatcher("/error/403/非法访问，您无权限执行此操作").forward(req, res);

    }

    private String checkRequestType(String uri, HttpServletRequest req) {
        log.debug("uri:{}", uri);
        return uri.contains("api") ? "json" : "error";
    }

}
