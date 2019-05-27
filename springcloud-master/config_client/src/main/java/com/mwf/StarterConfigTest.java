package com.mwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarterConfigTest {
	public static void main(String[] args) {
		SpringApplication.run(StarterConfigTest.class, args);
		
		System.out.println("\n 配置中心客户端启动成功...");
	}
}
