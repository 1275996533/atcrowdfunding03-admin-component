package com.hzj.crowd.mvc.config;

import com.google.gson.Gson;
import com.hzj.crowd.exception.AccessForbidException;
import com.hzj.crowd.exception.AdminAcctInUseException;
import com.hzj.crowd.exception.AdminAcctInUseForEditException;
import com.hzj.crowd.exception.LoginFailedException;
import com.hzj.crowd.util.CrowdUtil;
import com.hzj.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CrowdExcepitonResolver
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/13 15:05
 * @Version 1.0
 **/
/*
 *    @ControllerAdvice 表示当前是一个基于注解的异常处理类
 * */
@ControllerAdvice
public class CrowdExceptionResolver {
    @ExceptionHandler(AdminAcctInUseForEditException.class)
    public ModelAndView AdminAcctInUseForEditExceptionResolver(AdminAcctInUseForEditException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断当前请求类型类型
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }
    @ExceptionHandler(AdminAcctInUseException.class)
    public ModelAndView AdminAcctInUseExceptionResolver(AdminAcctInUseException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断当前请求类型类型
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }
    //此注解让方法与请求抛出的异常进行映射
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointExceptionResolver(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断当前请求类型类型
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView nullPointExceptionResolver(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断当前请求类型类型
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = AccessForbidException.class)
    public ModelAndView resolveAccessForbiddenException(AccessForbidException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    public ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean judge = CrowdUtil.judgeRequestType(request);
        if (judge) {
            //取得带异常消息的ResultEntity对象
            ResultEntity resultEntity = ResultEntity.failed(exception.getMessage());
            //获取gson实例
            Gson gson = new Gson();
            //将resultEntity转换为Json格式
            String result = gson.toJson(resultEntity);
            //将json直接回传给页面不经过视图解析器
            response.getWriter().write(result);
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        //将异常存入modelAndView
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
