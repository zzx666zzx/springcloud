package com.mwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * 接口提供者，供ribbon和feign调用
 * @author 郑仔祥
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class StartrerEurekaClient01 {
	public static void main(String[] args) {
		SpringApplication.run(StartrerEurekaClient01.class, args);
	}
}

