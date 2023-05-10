package com.gec.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysMenu;
import com.gec.model.system.SysRole;
import com.gec.model.vo.AssginMenuVo;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.service.SysRoleService;
import com.gec.system.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/7 14:29
 */
@Api(tags = "角色管理控制器")
@RestController
@RequestMapping("/admin/system/sysRole")

public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@ApiOperation("查询所有角色")
	@GetMapping("/findAll")
	public Result findAll(){
		List<SysRole> list = this.sysRoleService.list();
		return Result.ok(list);
	}

	@PreAuthorize("hasAuthority('bnt.sysRole.remove')")
	@ApiOperation("根据ID删除角色")
	@DeleteMapping("removeRoleById/{id}")
	public Result removeRoleById(@PathVariable Long id){
		boolean b = this.sysRoleService.removeById(id);
		if(b){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@PreAuthorize("hasAuthority('bnt.sysRole.list')")
	@GetMapping("/{page}/{limit}")
	public Result findRoleByPage(@PathVariable Long page, @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo){
		IPage<SysRole> sysRolePage= new Page<>(page,limit);
		sysRolePage = this.sysRoleService.selectRoleByPage(sysRolePage,sysRoleQueryVo);
		return Result.ok(sysRolePage);
	}

	@ApiOperation("根据ID查询一个角色")
	@PostMapping("/findSysRoleById/{id}")
	public Result findSysRoleById(@PathVariable Long id){
		SysRole result = this.sysRoleService.getById(id);
		return Result.ok(result);
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.update')")
	@ApiOperation("根据ID修改角色")
	@PostMapping("/updateSysRoleById")
	public Result updateSysRoleById(@RequestBody SysRole sysRole){
		boolean update = this.sysRoleService.updateById(sysRole);
		if(update){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@PreAuthorize("hasAuthority('bnt.sysRole.add')")
	@ApiOperation("添加一个角色")
	@PostMapping("saveByRole")
	public Result saveByRole(@RequestBody SysRole sysRole){
		boolean save = this.sysRoleService.save(sysRole);
		if (save)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}

	@PreAuthorize("hasAuthority('bnt.sysRole.remove')")
	@ApiOperation("批量删除")
	@DeleteMapping("batchRemove")
	public Result batchRemove(@RequestBody List<Long> ids)
	{
		boolean isSuccess = this.sysRoleService.removeByIds(ids);
		if (isSuccess)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}


	@ApiOperation(value = "根据用户获取角色数据")
	@GetMapping("/toAssign/{userId}")
	public Result toAssign(@PathVariable Long userId) {
		Map<String, Object> roleMap = sysRoleService.getRolesByUserId(userId);
		return Result.ok(roleMap);
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
	@ApiOperation(value = "根据用户分配角色")
	@PostMapping("/doAssign")
	public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
		sysRoleService.doAssign(assginRoleVo);
		return Result.ok();
	}

	// 根据角色分配菜单
	@ApiOperation("根据角色获取菜单")
	@GetMapping("/findMenuByRoleId/{roleId}")
	public Result findMenuByRoleId(@PathVariable Long roleId){
		List<SysMenu> list=this.sysRoleService.findMenuByRoleId(roleId);
		return Result.ok(list);
	}


	@PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
	@ApiOperation(value = "给角色分配权限")
	@PostMapping("/updateMenuByRoleId")
	public Result updateMenuByRoleId(@RequestBody AssginMenuVo assginMenuVo) {
		sysRoleService.updateMenuByRoleId(assginMenuVo);
		return Result.ok();
	}
}
