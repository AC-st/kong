package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.gec.model.vo.RouterVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.mapper.SysUserMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-04-11
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysMenuService sysMenuService;


	@Override
	public IPage<SysUser> selectUserByPage(IPage<SysUser> sysUserPage, SysUserQueryVo sysUserQueryVo) {
		return this.baseMapper.selectUserByPage(sysUserPage,sysUserQueryVo);
	}

	@Override
	public Boolean updateStatusById(Long id, Integer status) {
		SysUser sysUser = this.baseMapper.selectById(id);
		sysUser.setStatus(status);
		int result = this.baseMapper.updateById(sysUser);
		if(result>0){
			return true;
		}
		return false;
	}

	@Override
	public SysUser getUserByUserName(String username) {
		QueryWrapper<SysUser> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("username",username);
		SysUser sysUser = this.baseMapper.selectOne(queryWrapper);
		return sysUser;
	}

	@Override
	public Map<String, Object> getUserInfoById(String userId) {
		Map<String, Object> map = new HashMap<>();
		//根据userID查询用户信息
		SysUser sysUser = this.baseMapper.selectById(userId);
		//根据用户ID查询菜单
		List<RouterVo> routerVoList=this.sysMenuService.findMenuListByUserId(Integer.parseInt(userId));
		//根据用户id获取用户按钮权限
		List<String> btnList = this.sysMenuService.findButtonListByUserId(Integer.parseInt(userId));
		//前端需要的数据
		map.put("name", sysUser.getName());
		map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		map.put("roles",  "[admin]");
		map.put("buttons",btnList);
		map.put("routers",routerVoList);
		return map;
	}


}
