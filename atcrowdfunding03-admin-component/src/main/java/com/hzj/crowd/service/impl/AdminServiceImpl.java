package com.hzj.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzj.crowd.constant.CrowdConstant;
import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.entity.AdminExample;
import com.hzj.crowd.exception.AdminAcctInUseException;
import com.hzj.crowd.exception.AdminAcctInUseForEditException;
import com.hzj.crowd.exception.LoginFailedException;
import com.hzj.crowd.mapper.AdminMapper;
import com.hzj.crowd.service.api.AdminService;
import com.hzj.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName AdminServiceImpl
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/12 23:55
 * @Version 1.0
 **/
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public int save(Admin admin) {
        return adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }


    @Override
    public Admin getAdminByloginAcct(String loginAcct, String userPswd) {
        //创建AdminExample 对象
        AdminExample adminExample = new AdminExample();
        //创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        //将查询条件封装
        criteria.andLoginAcctEqualTo(loginAcct);
        //调用AdminMapper的方法进行查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        //判断admin是否有效
        if (admins == null || admins.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size()>1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = admins.get(0);
        if (admin!=null){
            String userPswdDb = admin.getUserPswd();
            String userPswdForm = bCryptPasswordEncoder.encode(userPswd);
            if (!bCryptPasswordEncoder.matches(userPswdDb, userPswdForm)){
                throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
            }
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        //调用pageHelper开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //执行查询语句
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);
        //封装到PageInfo中
        return new PageInfo<>(admins);
    }

    @Override
    public void removeAdmin(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public void saveAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        String currentTime = CrowdUtil.getCurrentTime();
        String encode = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(encode);
        admin.setCreateTime(currentTime);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException){
                throw new AdminAcctInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminByAdminId(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException){
                throw new AdminAcctInUseForEditException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void createAssign(Integer adminId, List<Integer> roleList) {
        adminMapper.deleteAssign(adminId);
        if (roleList!=null && roleList.size()!=0){
            adminMapper.createAssign(adminId,roleList);
        }

    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        Admin admin = admins.get(0);
        return admin;
    }
}
