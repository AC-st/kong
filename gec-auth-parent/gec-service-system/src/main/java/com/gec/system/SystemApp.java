package com.gec.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * @description:
 * @author: Admin
 * @time: 2023/4/7 10:34
 */

@SpringBootApplication
@MapperScan("com.gec.system.mapper")
public class SystemApp {
	public static void main(String[] args) {
		SpringApplication.run(SystemApp.class,args);
	}
}
