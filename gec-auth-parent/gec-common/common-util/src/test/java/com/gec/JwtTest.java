package com.gec;

import com.gec.system.util.JwtHelper;
import org.junit.Test;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/13 15:03
 */
public class JwtTest {

	@Test
	public void jwtTest(){
		String token = JwtHelper.createToken("1", "admin");
		System.out.println("令牌为："+token);
		System.out.println("================================");
		String userId = JwtHelper.getUserId(token);
		System.out.println("userId为："+userId);
		String userName = JwtHelper.getUsername(token);
		System.out.println("userId为："+userName);
	}
}
