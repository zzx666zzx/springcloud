package com.mwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableFeignClients //开启feigin注解
@EnableHystrix //开启Hystrix
@EnableDiscoveryClient //开启注册中心
@SpringBootApplication
public class FeignhystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignhystrixApplication.class, args);
	}

}
