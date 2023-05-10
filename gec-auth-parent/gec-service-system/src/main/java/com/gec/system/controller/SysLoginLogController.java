package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.system.common.Result;
import com.gec.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-04-20
 */

@Api("登录日志控制器")
@RestController
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {

	@Autowired
	private SysLoginLogService sysLoginLogService;
	//分页查询

	@ApiOperation(value = "获取分页列表")
	@GetMapping("/{page}/{limit}")
	public Result findLogByPage(@PathVariable Long page, @PathVariable Long limit, SysLoginLogQueryVo sysLoginLogQueryVo){
		IPage<SysLoginLog> iPage = sysLoginLogService.selectPage(page, limit, sysLoginLogQueryVo);
		return Result.ok(iPage);
	}

	@ApiOperation(value = "获取")
	@GetMapping("get/{id}")
	public Result get(@PathVariable Long id) {
		SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
		return Result.ok(sysLoginLog);
	}

	@ApiOperation(value = "根据ID进行删除")
	@DeleteMapping("/deleteLogById/{id}")
	public Result deleteLogById(@PathVariable Long id){
		boolean b = this.sysLoginLogService.removeById(id);
		if(b){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

}

