package com.hzj.crowd.mvc.config;

import com.hzj.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName SecurityUser
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/19 16:13
 * @Version 1.0
 **/
public class SecurityUser extends User {
    private Admin admin;

    public SecurityUser(Admin admin, List<GrantedAuthority> authorities) {
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}
