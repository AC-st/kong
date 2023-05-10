package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysCategory;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysCategoryService;
import com.gec.system.util.MD5Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-04-14
 */
@RestController
@Api(tags = "分类管理控制器")
@RequestMapping("/admin/system/SysCategory")
@CrossOrigin
public class SysCategoryController {

	@Autowired
	private SysCategoryService categoryService;

	@ApiOperation("查询全部分类")
	@GetMapping("/findAll")
	public Result findAll(){
		List<SysCategory> list = this.categoryService.list();
		return Result.ok(list);
	}

	@ApiOperation("分页+条件查询")
	@GetMapping("/{page}/{limit}")
	public Result findRoleByPage(@PathVariable Long page, @PathVariable Long limit, SysCategoryQueryVo sysCategoryQueryVo){
		IPage<SysCategory> sysCategoryPage= new Page<>(page,limit);
		sysCategoryPage = this.categoryService.selectCategoryByPage(sysCategoryPage,sysCategoryQueryVo );
		return Result.ok(sysCategoryPage);
	}


	@ApiOperation("添加分类")
	@PostMapping("saveByCategory")
	public Result saveByCategory(@RequestBody SysCategory sysCategory){
		boolean save = this.categoryService.save(sysCategory);
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
	@PostMapping("/findCategoryById/{id}")
	public Result findCategoryById(@PathVariable Long id){
		SysCategory result = this.categoryService.getById(id);
		return Result.ok(result);
	}

	@ApiOperation("根据ID删除用户")
	@DeleteMapping("removeCategroyById/{id}")
	public Result removeCategroyById(@PathVariable Long id){
		boolean b = this.categoryService.removeById(id);
		if(b){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	@ApiOperation("批量删除")
	@DeleteMapping("batchRemoveCategroy")
	public Result batchRemoveCategroy(@RequestBody List<Long> ids)
	{
		boolean isSuccess = this.categoryService.removeByIds(ids);
		if (isSuccess)
		{
			return Result.ok();
		}
		else
		{
			return Result.fail();
		}
	}

//	@ApiOperation("修改状态")
//	@PostMapping("/updateUserStatByid/{id}/{status}")
//	public Result updateUserStatByid(@PathVariable Long id,@PathVariable Integer status){
//		Boolean update=this.categoryService.updateStatusById(id,status);
//		if(update){
//			return Result.ok();
//		}else{
//			return Result.fail();
//		}
//	}

	@ApiOperation("根据ID修改分类")
	@PostMapping("/updateCategoryById")
	public Result updateCategoryById(@RequestBody SysCategory sysCategory){
		boolean update = this.categoryService.updateById(sysCategory);
		if(update){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}
}

