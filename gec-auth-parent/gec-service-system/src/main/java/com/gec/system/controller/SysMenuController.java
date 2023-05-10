package com.gec.system.controller;


import com.gec.model.system.SysMenu;
import com.gec.system.service.SysMenuService;
import com.gec.system.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-04-12
 */

@Api(tags = "菜单管理控制器")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

	@Autowired
	private SysMenuService sysMenuService;

	@ApiOperation("菜单列表")
	@GetMapping("/findNodes")
	public Result findNodes(){
		List<SysMenu> menuList=this.sysMenuService.findNodes();
		return Result.ok(menuList);
	}

	// 添加菜单
	@PreAuthorize("hasAuthority('bnt.sysMenu.add')")
	@ApiOperation("添加菜单")
	@PostMapping("/addMenu")
	public Result addMenu(@RequestBody SysMenu sysMenu){
		this.sysMenuService.save(sysMenu);
		return Result.ok();
	}

	//根据ID查询菜单
	@PreAuthorize("hasAuthority('bnt.sysMenu.update')")
	@ApiOperation("根据ID查询菜单")
	@PostMapping("getMenuById/{id}")
	public Result getMenuById(@PathVariable Long id){
		SysMenu sysMenu = this.sysMenuService.getById(id);
		return Result.ok(sysMenu);
	}

	//修改菜单
	@PreAuthorize("hasAuthority('bnt.sysMenu.update')")
	@ApiOperation("修改菜单")
	@PostMapping("updateMenu")
	public Result updateMenu(@RequestBody SysMenu sysMenu){
		boolean result = this.sysMenuService.updateById(sysMenu);
		if(result){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	//删除菜单
	@PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
	@ApiOperation("根据ID删除菜单")
	@DeleteMapping("deleteMenuById/{id}")
	public Result deleteMenuById(@PathVariable Long id){
		boolean result = this.sysMenuService.removeById(id);
		if(result){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@ApiOperation("修改菜单状态")
	@PreAuthorize("hasAuthority('bnt.sysMenu.update')")
	@PostMapping("/updateMenuStatus/{id}/{status}")
	public Result updateMenuStatus(@PathVariable Long id,@PathVariable Integer status){
		Boolean result=this.sysMenuService.updateStatus(id,status);

		if(result){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}


}

