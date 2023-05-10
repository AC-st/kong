package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gec.model.system.SysMenu;
import com.gec.model.vo.RouterVo;
import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.system.util.RouterHelper;
import com.gec.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-04-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenu> findNodes() {
		//查询所有菜单
		List<SysMenu> list = this.sysMenuMapper.selectList(null);
		//将所有菜单转成树形
		List<SysMenu> menuList = MenuHelper.bulidTree(list);
		return menuList;
	}

	@Override
	public Boolean updateStatus(Long id, Integer status) {
		SysMenu sysMenu = this.sysMenuMapper.selectById(id);
		sysMenu.setStatus(status);
		int update = this.sysMenuMapper.updateById(sysMenu);
		UpdateWrapper<SysMenu> wrapper = new UpdateWrapper<>();
		wrapper.eq("parent_id",id);
		wrapper.set("status",status);
//		List<SysMenu> menuList = this.baseMapper.selectList(wrapper);
		this.sysMenuMapper.update(null,wrapper);

		if(update>0){
			return true;
		}
		return false;

	}

	@Override
	public List<RouterVo> findMenuListByUserId(int userId) {
		List<SysMenu> menuList=null;
		//超级管理员为id为1
		if(userId == 1){
			QueryWrapper<SysMenu> warpper=new QueryWrapper<>();
			warpper.eq("status",1);
			warpper.orderByAsc("sort_value");
			menuList = this.baseMapper.selectList(warpper);
		}else{
			menuList=this.baseMapper.findMenuListByUserId(userId);
		}
		//构建树形数据
		List<SysMenu> bulidTree = MenuHelper.bulidTree(menuList);
		//构建路由
		List<RouterVo> routerVoList = RouterHelper.buildRouters(bulidTree);

		return routerVoList;
	}

	@Override
	public List<String> findButtonListByUserId(int userId) {
		List<SysMenu> sysMenuList = null;
		//超级管理员查询全部 ID为1
		if(userId == 1){
			QueryWrapper<SysMenu> querWarpper=new QueryWrapper<>();
			querWarpper.eq("status",1);
			sysMenuList = this.baseMapper.selectList(querWarpper);
		}else{
			sysMenuList = this.baseMapper.findMenuListByUserId(userId);
		}
		//创建返回的集合
		ArrayList<String> list = new ArrayList<>();
		for (SysMenu sysMenu : sysMenuList) {
			if(sysMenu.getType() == 2){
				list.add(sysMenu.getPerms());
			}
		}
		return list;
	}
}
