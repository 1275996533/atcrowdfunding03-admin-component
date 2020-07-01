//package com.hzj.crowd.mvc.interceptor;
//
//import com.hzj.crowd.constant.CrowdConstant;
//import com.hzj.crowd.entity.Admin;
//import com.hzj.crowd.exception.AccessForbidException;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * @ClassName LoginInterceptor
// * @Description TODO
// * @Author 黄政杰
// * @Date 2020/6/13 23:46
// * @Version 1.0
// **/
//public class LoginInterceptor extends HandlerInterceptorAdapter {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//
//        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_Admin_LOGIN);
//
//        if (admin == null) {
//            throw new AccessForbidException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
//        }
//        return true;
//    }
//}
