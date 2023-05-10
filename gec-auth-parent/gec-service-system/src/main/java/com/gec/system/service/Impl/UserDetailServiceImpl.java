package com.gec.system.service.Impl;

import com.gec.model.system.SysUser;
import com.gec.system.custom.CustomUser;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/17 16:16
 */
@Component("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMenuService sysMenuService;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		//1.根据用户名获取用户信息
		SysUser sysUser = this.sysUserService.getUserByUserName(userName);
		//用户为空
		if(sysUser == null){
			throw new InternalAuthenticationServiceException("用户名不存在！");
		}
		if(sysUser.getStatus() == 0){
			throw new RuntimeException("账号冻结！");
		}
		List<String> buttonList = this.sysMenuService.findButtonListByUserId(Integer.parseInt(sysUser.getId().toString()));
		List<GrantedAuthority> list = new ArrayList<>();
		for (String pers : buttonList) {
			list.add(new SimpleGrantedAuthority(pers));
		}
		return new CustomUser(sysUser, list);
	}
}
