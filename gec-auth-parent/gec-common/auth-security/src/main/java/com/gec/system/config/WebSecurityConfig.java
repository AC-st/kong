package com.gec.system.config;

import com.gec.system.custom.CustomMd5PasswordEncoder;
import com.gec.system.custom.TokenAuthenticationFilter;
import com.gec.system.custom.TokenLoginFilter;
import com.gec.system.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/17 15:06
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomMd5PasswordEncoder customMd5PasswordEncoder;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()     //关闭csrf
				.cors().and().authorizeRequests()       //开启跨域
				.antMatchers("/admin/system/SysCategory/findAll").permitAll()
				.antMatchers("/admin/system/upload/uploadImage").permitAll()
				.antMatchers("/admin/system/upload/uploadVideo").permitAll()
				.antMatchers("/admin/system/index/login").permitAll()
				.antMatchers("/admin/system/sysMovie/findMovieAll").permitAll()
				.antMatchers("/admin/system/sysMovie/findMovieByCateId").permitAll()
				.antMatchers("/admin/system/sysMovie/getPlayAuth").permitAll()
				// 这里意思是其它所有接口需要认证才能访问  getPlayAuth
				.anyRequest().authenticated()
				.and()
				//TokenAuthenticationFilter放到UsernamePasswordAuthenticationFilter的前面，这样做就是为了除了登录的时候去查询数据库外，其他时候都用token进行认证。
				.addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
				.addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate,sysLoginLogService));

		//禁用session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 指定UserDetailService和加密器
		auth.userDetailsService(userDetailsService).passwordEncoder(customMd5PasswordEncoder);
	}

	/**
	 * 配置哪些请求不拦截
	 * 排除swagger相关请求
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
	}





}
