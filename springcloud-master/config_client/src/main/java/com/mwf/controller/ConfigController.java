package com.mwf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConfigController {
	
	
	@Value("${user.name}")
	private String name;
	
	@Value("${user.sex}")
	private String sex;
	
	@Value("${user.age}")
	private String age;
	
	
	@RequestMapping("config")
	public String getConfig(){
		return "姓名："+name+"\n年龄："+age+"\n性别："+sex;
	}
	
}
