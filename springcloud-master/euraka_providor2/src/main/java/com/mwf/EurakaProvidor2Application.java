package com.mwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 生产者同一接口模拟集群，ribbon默认的负载均衡策略是轮询的
 * @author 郑仔祥
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class EurakaProvidor2Application {

	public static void main(String[] args) {
		SpringApplication.run(EurakaProvidor2Application.class, args);
	}

}
