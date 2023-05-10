package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2023-04-11
 */
public interface SysUserService extends IService<SysUser> {

	IPage<SysUser> selectUserByPage(IPage<SysUser> sysUserPage, SysUserQueryVo sysUserQueryVo);

	//修改状态
	Boolean updateStatusById(Long id, Integer status);

	SysUser getUserByUserName(String username);

	Map<String, Object> getUserInfoById(String userId);
}
