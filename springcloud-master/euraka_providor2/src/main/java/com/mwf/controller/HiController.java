package com.mwf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
	@Value("${server.port}")
	private String port;
	@RequestMapping("hi")
	public String hi(){
		return "hi"+port;
	}
	
	@RequestMapping("hi1")
	public String hi1(String name){
		return "生产者2.."+port+name;
	}
	
	/**
	 * 模拟一个超时服务
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("/timeout")
    public String timeout() throws InterruptedException {
        System.out.println("invoking timeout endpoint");
        Thread.sleep(10000L);
        return "success";
    }

	/**
	 * 模拟一个异常服务
	 * @return
	 */
	@RequestMapping("/exception")
    public String exception() {
		System.out.println("invoking exception endpoint");
        if (System.currentTimeMillis() % 2 == 0) {
            throw new RuntimeException("random exception");
        }
        return "success";
    }

	

}
