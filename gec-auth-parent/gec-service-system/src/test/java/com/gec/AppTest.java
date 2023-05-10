package com.gec;

import static org.junit.Assert.assertTrue;

import com.gec.model.system.SysRole;
import com.gec.system.SystemApp;
import com.gec.system.mapper.SysRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = SystemApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void fun1()
    {
        List<SysRole> sysRoles = this.sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }
}
