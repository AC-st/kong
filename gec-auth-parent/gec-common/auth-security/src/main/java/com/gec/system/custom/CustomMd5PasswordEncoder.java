package com.gec.system.custom;

import com.gec.system.util.MD5Helper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/17 15:33
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return MD5Helper.encrypt(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String s) {
		return s.equals(MD5Helper.encrypt(rawPassword.toString()));
	}
}
