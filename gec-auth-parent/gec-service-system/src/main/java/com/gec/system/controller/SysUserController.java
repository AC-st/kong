package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.service.SysUserService;
import com.gec.system.util.MD5Helper;
import com.gec.system.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-04-11
 */
@RestController
@Api(tags = "用户控制器")
@RequestMapping("/admin/system/sysUser")

public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

//	@ApiOperation("查询所有用户")
//	@GetMapping("/findAll")
//	public Result findAll(){
//		List<SysUser> list = this.sysUserService.list();
//		return Result.ok(list);
//	}

	@PreAuthorize("hasAuthority('bnt.sysUser.list')")
	@ApiOperation("分页+条件查询")
	@GetMapping("/{page}/{limit}")
	public Result findRoleByPage(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo){
		IPage<SysUser> sysUserPage= new Page<>(page,limit);
		sysUserPage = this.sysUserService.selectUserByPage(sysUserPage, sysUserQueryVo);
		return Result.ok(sysUserPage);
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.add')")
	@ApiOperation("添加一个用户")
	@PostMapping("saveByUser")
	public Result saveByRole(@RequestBody SysUser sysUser){
		//将明文转为密文
		sysUser.setPassword(MD5Helper.encrypt(sysUser.getPassword()));
		boolean save = this.sysUserService.save(sysUser);
		if (save)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}

	@ApiOperation("根据ID查询一个用户")
	@PostMapping("/findUserById/{id}")
	public Result findSysRoleById(@PathVariable Long id){
		SysUser result = this.sysUserService.getById(id);
		return Result.ok(result);
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.remove')")
	@ApiOperation("根据ID删除用户")
	@DeleteMapping("removeUserById/{id}")
	public Result removeRoleById(@PathVariable Long id){
		boolean b = this.sysUserService.removeById(id);
		if(b){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.remove')")
	@ApiOperation("批量删除")
	@DeleteMapping("batchRemoveUser")
	public Result batchRemove(@RequestBody List<Long> ids)
	{
		boolean isSuccess = this.sysUserService.removeByIds(ids);
		if (isSuccess)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.update')")
	@ApiOperation("修改状态")
	@PostMapping("/updateUserStatByid/{id}/{status}")
	public Result updateUserStatByid(@PathVariable Long id,@PathVariable Integer status){
		Boolean update=this.sysUserService.updateStatusById(id,status);
		if(update){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@PreAuthorize("hasAuthority('bnt.sysUser.update')")
	@ApiOperation("根据ID修改角色")
	@PostMapping("/updateSysRoleById")
	public Result updateSysUserById(@RequestBody SysUser sysUser){
		boolean update = this.sysUserService.updateById(sysUser);
		if(update){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}



}

