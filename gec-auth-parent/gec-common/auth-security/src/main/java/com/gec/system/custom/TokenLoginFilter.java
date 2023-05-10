package com.gec.system.custom;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.model.system.SysUser;
import com.gec.model.vo.LoginVo;
import com.gec.system.common.Result;
import com.gec.system.common.ResultCodeEnum;
import com.gec.system.service.SysLoginLogService;
import com.gec.system.util.IpUtil;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.ResponseUtil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/17 15:35
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	//注入redisTemplate
	private RedisTemplate redisTemplate;
	private SysLoginLogService sysLoginLogService;
	
	public TokenLoginFilter(AuthenticationManager authenticationManager,
	                        RedisTemplate redisTemplate,
	                        SysLoginLogService sysLoginLogService) {
		this.setPostOnly(false);
		this.setAuthenticationManager(authenticationManager);
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
		this.redisTemplate=redisTemplate;
		this.sysLoginLogService=sysLoginLogService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
			return this.getAuthenticationManager().authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		CustomUser customUser= (CustomUser) authResult.getPrincipal();
		SysUser sysUser = customUser.getSysUser();
		String token = JwtHelper.createToken(sysUser.getId().toString(), sysUser.getUsername());
		//将数据保存到redis中
		redisTemplate.opsForValue().set(sysUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));
		sysLoginLogService.recordLoginLog(customUser.getUsername(),0, IpUtil.getIpAddress(request),"登录成功！");
		HashMap<Object, String> map = new HashMap<>();
		map.put("token",token);
		ResponseUtil.out(response, Result.ok(map));
	}

	@Override

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		System.out.println(failed);

		if(failed.getCause() instanceof RuntimeException){
			ResponseUtil.out(response,Result.build(null,ResultCodeEnum.ACCOUNT_STOP));
		}else if (failed.getClass().equals(BadCredentialsException.class) ){
			ResponseUtil.out(response,Result.build(null,ResultCodeEnum.PASSWORD_ERROR));
		}else if(failed.getClass().equals(InternalAuthenticationServiceException.class)){
			ResponseUtil.out(response,Result.build(null,ResultCodeEnum.LOGIN_MOBLE_ERROR));
		}
	}
}
