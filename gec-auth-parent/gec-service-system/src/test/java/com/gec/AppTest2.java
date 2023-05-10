package com.gec;

import com.gec.model.system.SysRole;
import com.gec.system.SystemApp;
import com.gec.system.service.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/7 11:11
 */
@SpringBootTest(classes = SystemApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest2 {

	@Autowired
	private SysRoleService sysRoleService;

	@Test
	public void fun1(){
		List<SysRole> list = this.sysRoleService.list();
		list.forEach(System.out::println);
	}

	@Test
	public void fun2(){
		SysRole sysRole = this.sysRoleService.getById(8L);
		System.out.println(sysRole);
	}

	@Test
	public void fun3(){
		SysRole sysrole = new SysRole() ;
		sysrole.setRoleName("测试");
		sysrole.setId(11L);
		boolean b = this.sysRoleService.updateById(sysrole);
	}

	@Test
	public void fun4(){
		this.sysRoleService.removeById(11L);
	}
}
