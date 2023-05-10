package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysMenu;
import com.gec.model.system.SysRole;
import com.gec.model.vo.AssginMenuVo;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;

import java.util.List;
import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

	IPage<SysRole> selectRoleByPage(IPage<SysRole> sysRolePage, SysRoleQueryVo sysRoleQueryVo);

	Map<String, Object> getRolesByUserId(Long userId);

	void doAssign(AssginRoleVo assginRoleVo);

	List<SysMenu> findMenuByRoleId(Long roleId);

	void updateMenuByRoleId(AssginMenuVo assginMenuVo);
}
