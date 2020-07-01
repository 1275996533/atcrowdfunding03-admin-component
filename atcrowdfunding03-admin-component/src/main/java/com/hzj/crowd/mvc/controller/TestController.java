package com.hzj.crowd.mvc.controller;

import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.entity.Student;
import com.hzj.crowd.service.api.AdminService;
import com.hzj.crowd.util.CrowdUtil;
import com.hzj.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/13 1:21
 * @Version 1.0
 **/
@Controller
public class TestController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSsm(Model model, HttpServletRequest request){
        List<Admin> admins = adminService.getAll();
        model.addAttribute("admins", admins);
       int i = 1/0;
        return "target";
    }
    @RequestMapping("/test/json.json")
    @ResponseBody
    public ResultEntity<Student> testJson(@RequestBody Student student,HttpServletRequest request){
        ResultEntity<Student> resultEntity = new ResultEntity<>();
        resultEntity.setQueryData(student);
        return resultEntity;
    }
}
