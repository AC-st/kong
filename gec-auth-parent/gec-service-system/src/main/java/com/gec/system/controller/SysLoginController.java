package com.gec.system.controller;

import com.gec.model.system.SysUser;
import com.gec.model.vo.LoginVo;
import com.gec.system.common.Result;
import com.gec.system.common.ResultCodeEnum;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.service.SysUserService;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.MD5Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/10 11:12
 */
@RestController
@Api(tags = "登录管理控制器")
@RequestMapping(value = "/admin/system/index")
public class SysLoginController {

	@Autowired
	private SysUserService sysUserService;

//	@ApiOperation("登录接口")
//	@PostMapping("/login")
//	public Result login(){
//		Map<String, Object> map = new HashMap<>();
//		map.put("token","admin-token");
//		return Result.ok(map);
//	}

	@ApiOperation("登录接口")
	@PostMapping("/login")
	public Result login(@RequestBody LoginVo loginVo){
		//1.根据username 查询数据
		SysUser sysUser =  this.sysUserService.getUserByUserName(loginVo.getUsername());

		//2.如果查询为空 给出提示
		if (sysUser==null){
			throw  new MyCustomerException(20001,"用户不存在..");
		}

		//3.比较密码  （使用用户输入的密码和数据库密码比较）
		String password = loginVo.getPassword();
		String passwordwithMD5 = MD5Helper.encrypt(password);

		if (!sysUser.getPassword().equals(passwordwithMD5)){
			throw new MyCustomerException(20001,"密码不正确");
		}


		//4.判断用户是否可用
		if (sysUser.getStatus().intValue()==0){
			throw new MyCustomerException(20001,"账号被停用..");
		}
		//5.根据userid和username 生成token字符串 再通过map返回
		//5.根据用户userid和username去生成token，再通过map封装后返回
		String token = JwtHelper.createToken(sysUser.getId()+"", sysUser.getUsername());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("token", token);

		return Result.ok(map);
	}

//	@ApiOperation("Info接口")
//	@GetMapping("info")
//	public Result info(HttpServletRequest request){
//		Map<String, Object> map = new HashMap<>();
//		map.put("roles","[admin]");
//		map.put("introduction","I am a super administrator");
//		map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//		map.put("name","Super Admin");
//		return Result.ok(map);
//	}

	@ApiOperation("Info接口")
	@GetMapping("info")
	public Result info(HttpServletRequest request){
		//从请求头中获取user信息
		String token = request.getHeader("token");
		//解析token，获取userName和userId
		String userId = JwtHelper.getUserId(token);
		String username = JwtHelper.getUsername(token);
		Map<String,Object> map=this.sysUserService.getUserInfoById(userId);
		return Result.ok(map);
	}

}
