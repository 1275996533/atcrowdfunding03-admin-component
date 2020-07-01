package com.hzj.crowd.mvc.controller;

import com.hzj.crowd.entity.Menu;
import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.service.api.MenuService;
import com.hzj.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/17 16:06
 * @Version 1.0
 **/
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    //@ResponseBody
    @RequestMapping("/menu/edit.json")
    public ResultEntity editMenu(Menu menu){
        menuService.editMenu(menu);
        return ResultEntity.successWithoutData();
    }
    //@ResponseBody
    @RequestMapping("/menu/remove.json")
    public ResultEntity removeMenu(@RequestParam("id") String id){
        menuService.removeMenu(Integer.parseInt(id));
        return ResultEntity.successWithoutData();
    }
    //@ResponseBody
    @RequestMapping("/menu/save.json")
    public ResultEntity saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }
    //@ResponseBody
    @RequestMapping("/menu/get/page.json")
    public ResultEntity<Menu> getMenu() {
        //获取所有的选项
        List<Menu> menus = menuService.getAll();
        //用来存放父节点
        Menu root = null;
        //将所有的节点存放入map集合中
        Map<Integer,Menu> menuMap = new HashMap<>();
        for (Menu menu : menus) {
            menuMap.put(menu.getId(), menu);
        }
        //
        for (Menu menu : menus) {
            Integer pid = menu.getPid();
            if (pid == null){
                root = menu;
                continue;
            }
            Menu parent = menuMap.get(pid);
            parent.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }
}
