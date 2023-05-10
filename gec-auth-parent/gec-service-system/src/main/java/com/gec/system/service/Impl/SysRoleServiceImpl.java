package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysMenu;
import com.gec.model.system.SysRole;
import com.gec.model.system.SysRoleMenu;
import com.gec.model.system.SysUserRole;
import com.gec.model.vo.AssginMenuVo;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.mapper.SysRoleMapper;
import com.gec.system.mapper.SysRoleMenuMapper;
import com.gec.system.mapper.SysUserRoleMapper;
import com.gec.system.service.SysRoleService;
import com.gec.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/7 11:10
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public IPage<SysRole> selectRoleByPage(IPage<SysRole> sysRolePage, SysRoleQueryVo sysRoleQueryVo) {
		IPage<SysRole> sysRoleIPage=this.baseMapper.selectRoleByPage(sysRolePage,sysRoleQueryVo);
		return sysRoleIPage;
	}

	@Override
	public Map<String, Object> getRolesByUserId(Long userId) {
		//获取所有角色
		List<SysRole> roles = sysRoleMapper.selectList(null);
		//根据用户id查询
		QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id",userId);
		//获取用户已分配的角色
		List<SysUserRole> userRoles = sysUserRoleMapper.selectList(queryWrapper);
		//获取用户已分配的角色id
		List<Long> userRoleIds = new ArrayList<>();
		for (SysUserRole userRole : userRoles) {
			userRoleIds.add(userRole.getRoleId());
		}
		//创建返回的Map
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("allRoles",roles);
		returnMap.put("userRoleIds",userRoleIds);
		return returnMap;
	}

	@Override
	public void doAssign(AssginRoleVo assginRoleVo) {
		//根据用户id删除原来分配的角色
		QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id",assginRoleVo.getUserId());
		sysUserRoleMapper.delete(queryWrapper);
		//获取所有的角色id
		List<Long> roleIdList = assginRoleVo.getRoleIdList();
		for (Long roleId : roleIdList) {
			if(roleId != null){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(assginRoleVo.getUserId());
				sysUserRole.setRoleId(roleId);
				//保存
				sysUserRoleMapper.insert(sysUserRole);
			}
		}
	}

	@Override
	public List<SysMenu> findMenuByRoleId(Long roleId) {

		//先查询状态未被禁用的菜单
		QueryWrapper<SysMenu> menuWrapper = new QueryWrapper<>();
		menuWrapper.eq("status",1);
		List<SysMenu> menuList = this.sysMenuMapper.selectList(menuWrapper);
		//根据角色id获取角色权限
		QueryWrapper<SysRoleMenu> roleMenuWrapper = new QueryWrapper<>();
		roleMenuWrapper.eq("role_id",roleId);
		List<SysRoleMenu> roleMenus = this.sysRoleMenuMapper.selectList(roleMenuWrapper);
		//获取该角色已分配的所有权限id
		List<Long> roleMenuIds = new ArrayList<>();
		for (SysRoleMenu roleMenu : roleMenus) {
			roleMenuIds.add(roleMenu.getMenuId());
		}
		//遍历所有权限列表
		for (SysMenu sysMenu : menuList) {
			if(roleMenuIds.contains(sysMenu.getId())){
				//设置该权限已被分配
				sysMenu.setSelect(true);
			}else {
				sysMenu.setSelect(false);
			}
		}
		List<SysMenu> sysMenus = MenuHelper.bulidTree(menuList);
		return sysMenus;
	}

	@Override
	public void updateMenuByRoleId(AssginMenuVo assginMenuVo) {
		//先删除当前角色下的菜单
		QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
		wrapper.eq("role_id",assginMenuVo.getRoleId());
		this.sysRoleMenuMapper.delete(wrapper);
		//循环添加
		for (Long menuId : assginMenuVo.getMenuIdList()) {
			if(menuId!=null){
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setMenuId(menuId);
				roleMenu.setRoleId(assginMenuVo.getRoleId());
				this.sysRoleMenuMapper.insert(roleMenu);
			}
		}
	}
}
