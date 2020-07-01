package com.hzj.crowd.service.impl;

import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.entity.AdminExample;
import com.hzj.crowd.entity.Auth;
import com.hzj.crowd.entity.AuthExample;
import com.hzj.crowd.mapper.AuthMapper;
import com.hzj.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AuthServiceImpl
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/18 13:55
 * @Version 1.0
 **/
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignByRoleId(Integer roleId) {
        return authMapper.selectAuthIdByRoleId(roleId);
    }

    @Override
    public void createAssign(List<Integer> authIdList, Integer roleId) {
        authMapper.deleteAssignByRoleId(roleId);
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.createAssignByAuthIds(authIdList, roleId);
        }
    }

    @Override
    public List<String> selectAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAuthNameByAdminId(adminId);
    }

}
