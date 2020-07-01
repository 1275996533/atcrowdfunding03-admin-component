
import com.hzj.crowd.entity.Admin;
import com.hzj.crowd.mapper.AdminMapper;
import com.hzj.crowd.mapper.RoleMapper;
import com.hzj.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * @ClassName TestDataSource
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/12 21:49
 * @Version 1.0
 **/
//spring 整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class TestCrowd {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void testTx(){
        adminService.save(new Admin(null,"tom","123456","tom","tom@qq.com",null));
    }
    @Test
    public void testInsert(){
        List<Admin> all = adminService.getAll();
        for (int i = 0;i<300;i++){
            adminMapper.insert(new Admin(null,"hzj","123"+i,"user","user@qq.com",null));
        }
    }
    @Test
    public void testConnection(){
        System.out.println(dataSource);
    }

}
