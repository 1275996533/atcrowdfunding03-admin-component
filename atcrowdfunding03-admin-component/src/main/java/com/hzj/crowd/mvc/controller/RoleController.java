package com.hzj.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.hzj.crowd.entity.Role;
import com.hzj.crowd.service.api.RoleService;
import com.hzj.crowd.util.ResultEntity;
import org.apache.taglibs.standard.lang.jstl.IntegerLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/15 22:52
 * @Version 1.0
 **/

//代替@Controller 和 @ResponseBody
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    //@ResponseBody
    @RequestMapping("/role/remove/role/by/roleId.json")
    public ResultEntity removeRole(@RequestBody List<String> arrayIdList){
       roleService.removeRoleByIds(arrayIdList);
        return ResultEntity.successWithoutData();
    }
    //@ResponseBody
    @RequestMapping("/role/edit.json")
    public ResultEntity editRole(Role role){
        roleService.editRole(role);
        return ResultEntity.successWithoutData();
    }
    //@ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }
    //@ResponseBody
    @RequestMapping("/role/get/page.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword
    ){

        PageInfo<Role> pageInfo;
        try {
            pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
            return ResultEntity.successWithData(pageInfo);
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }
    }
}
