package com.hzj.crowd.service.api;

import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.entity.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> getAll();
    List<Integer> getAssignByRoleId(Integer roleId);
    void createAssign(List<Integer> authIdList, Integer roleId);
    List<String> selectAuthNameByAdminId(Integer adminId);

}
