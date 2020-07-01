package com.hzj.crowd.service.impl;

import com.hzj.crowd.entity.Menu;
import com.hzj.crowd.mapper.MenuMapper;
import com.hzj.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/17 16:03
 * @Version 1.0
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getAll() {
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void editMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }
}
