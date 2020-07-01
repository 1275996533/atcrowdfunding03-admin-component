package com.hzj.crowd.service.api;

import com.hzj.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();
    void saveMenu(Menu menu);
    void removeMenu(Integer id);
    void editMenu(Menu menu);
}
