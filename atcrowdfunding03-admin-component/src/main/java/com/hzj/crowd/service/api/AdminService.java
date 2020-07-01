package com.hzj.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.entity.Role;

import java.util.List;

/**
 * @ClassName AdminService
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/12 23:54
 * @Version 1.0
 **/
public interface AdminService {
    int save(Admin admin);
    List<Admin> getAll();
    Admin getAdminByloginAcct(String loginAcct, String userPswd);
    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);
    void removeAdmin(Integer adminId);
    void saveAdmin(Admin admin);
    Admin getAdminByAdminId(Integer adminId);
    void updateAdmin(Admin admin);
    void createAssign(Integer adminId, List<Integer> roleList);
    Admin getAdminByLoginAcct(String loginAcct);
}
