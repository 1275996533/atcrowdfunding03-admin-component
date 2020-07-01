package com.hzj.crowd.mvc.controller;

import com.hzj.crowd.entity.Auth;
import com.hzj.crowd.entity.Role;
import com.hzj.crowd.mapper.AdminMapper;
import com.hzj.crowd.mapper.RoleMapper;
import com.hzj.crowd.service.api.AdminService;
import com.hzj.crowd.service.api.AuthService;
import com.hzj.crowd.service.api.RoleService;
import com.hzj.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName AssignController
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/17 23:53
 * @Version 1.0
 **/
@Controller
public class AssignController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/save/assign.json")
    public ResultEntity saveAssignForRole(
            @RequestParam(value = "checkedNodeIds[]",required = false) List authIdList ,
            @RequestParam("roleId") Integer roleId
    ){
        authService.createAssign(authIdList,roleId);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("/get/assign/by/roleId.json")
    public ResultEntity<List<Integer>> getAssignByRoleId(
            @RequestParam("roleId") Integer roleId
            ) {
        List<Integer> authIds = authService.getAssignByRoleId(roleId);
        return ResultEntity.successWithData(authIds);
    }

    @ResponseBody
    @RequestMapping("/get/assign/auth.page.json")
    public ResultEntity<List<Auth>> getAuthPage() {
        List<Auth> auths = authService.getAll();
        return ResultEntity.successWithData(auths);
    }

    @RequestMapping("assign/do/save/assign.html")
    public String saveAssign(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam("adminId") Integer adminId,
            @RequestParam(value = "roleList", required = false) List<Integer> roleList
    ) {
        adminService.createAssign(adminId, roleList);
        return "redirect:/admin/get/page.html" + "?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("assign/to/assign/page.html")
    public String toAssignPage(
            @RequestParam("adminId") Integer adminId,
            Model model
    ) {
        List<Role> assignRoleList = roleService.getAssignRole(adminId);
        List<Role> unAssignRoleList = roleService.getUnAssignRole(adminId);
        model.addAttribute("assignRoleList", assignRoleList);
        model.addAttribute("unAssignRoleList", unAssignRoleList);
        return "assign-role";
    }
}
