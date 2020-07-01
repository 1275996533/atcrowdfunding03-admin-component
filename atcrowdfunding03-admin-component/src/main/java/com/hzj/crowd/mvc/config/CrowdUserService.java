package com.hzj.crowd.mvc.config;

import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.entity.Role;
import com.hzj.crowd.service.api.AdminService;
import com.hzj.crowd.service.api.AuthService;
import com.hzj.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CrowdUserService
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/19 16:47
 * @Version 1.0
 **/
@Component
public class CrowdUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        //通过登录账号查询出Admin
        Admin admin = adminService.getAdminByLoginAcct(loginAcct);
        //取出admin的id
        Integer adminId = admin.getId();
        //
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //通过adminId取出对应的角色
        List<Role> roles = roleService.getAssignRole(adminId);
        for (Role role : roles) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        //通过adminId取出对应的权限
        List<String> list = authService.selectAuthNameByAdminId(adminId);
        for (String auth : list) {
            authorityList.add(new SimpleGrantedAuthority(auth));
        }
        SecurityUser securityUser = new SecurityUser(admin, authorityList);

        return securityUser;
    }
}
