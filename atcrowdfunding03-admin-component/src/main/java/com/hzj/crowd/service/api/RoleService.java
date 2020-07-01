package com.hzj.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.hzj.crowd.entity.Role;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);
    void saveRole(Role role);
    void editRole(Role role);
    void removeRoleByIds(List arrayIdList);
    List<Role> getUnAssignRole(Integer adminId);
    List<Role> getAssignRole(Integer adminId);
}
