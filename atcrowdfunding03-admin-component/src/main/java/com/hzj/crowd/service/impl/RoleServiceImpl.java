package com.hzj.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzj.crowd.entity.Role;
import com.hzj.crowd.entity.RoleExample;
import com.hzj.crowd.mapper.RoleMapper;
import com.hzj.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/15 22:53
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
       //1.让mybatis开启分页功能
        PageHelper.startPage(pageNum, pageSize);
       //2.查询出Role列表
        List<Role> roles = roleMapper.selectRoleByKeyWord(keyword);
       //3.把List包装成PageInfo
        return new PageInfo<>(roles);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void editRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRoleByIds(List arrayIdList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(arrayIdList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getUnAssignRole(Integer adminId) {
        return roleMapper.selectUnAssignRole(adminId);
    }

    @Override
    public List<Role> getAssignRole(Integer adminId) {
        return roleMapper.selectAssignRole(adminId);
    }
}
