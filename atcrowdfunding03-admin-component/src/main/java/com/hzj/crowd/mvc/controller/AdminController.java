package com.hzj.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.hzj.crowd.constant.CrowdConstant;
import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.service.api.AdminService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/13 21:34
 * @Version 1.0
 **/
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("edit/admin.html")
    public String editAdmin(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ){
        adminService.updateAdmin(admin);
        return "redirect:/admin/get/page.html"+"?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            Model model,
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ){
        Admin admin = adminService.getAdminByAdminId(adminId);
        model.addAttribute("admin", admin);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("keyword", keyword);
        return "admin-edit";
    }
    @RequestMapping("/save/admin.html")
    public String saveAdmin(Admin admin){
        System.out.println(1);
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String deleteAdmin(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ){
        adminService.removeAdmin(adminId);
        return "redirect:/admin/get/page.html"+"?pageNum=" + pageNum + "&keyword=" + keyword;
    }
    @PreAuthorize(value ="hasRole('部长')")
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            Model model
    ){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        model.addAttribute(CrowdConstant.ATTR_PAGE_INFO, pageInfo);
        return "admin-page";
    }
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session){
        Admin admin = adminService.getAdminByloginAcct(loginAcct,userPswd);
        session.setAttribute(CrowdConstant.ATTR_Admin_LOGIN, admin);
        return "redirect:/admin/to/main/page.html";
    }
    @RequestMapping("/admin/do/logout.html")
    public String logout(HttpSession session){
        //强制session失效
        session.invalidate();
        //重定向回登陆页面
        return "redirect:/admin/to/login/page.html";
    }
}
