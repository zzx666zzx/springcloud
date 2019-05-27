package com.mwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * eureka注册中心
 * @author 郑仔祥
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class StarterEurekaServer {
	public static void main(String[] args) {
		SpringApplication.run(StarterEurekaServer.class, args);
	}
}

 
