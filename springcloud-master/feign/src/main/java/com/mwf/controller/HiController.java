package com.mwf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mwf.service.HiService;

@Controller
public class HiController {
	@Autowired
	private HiService hiService;
	
	@RequestMapping("hi")
	@ResponseBody
	public String sayHi(String name ){
		return name+","+hiService.hi(name);
	}
	
	@RequestMapping("zuul")
	@ResponseBody
	public String sayHi(){
		return "this is feign zuul!";
	}
	
	//重定向到hello.jsp页面，测试使用，目前没有这个页面
	@RequestMapping("hello4")
	public String sayHello4(){
		return "redirect:/hello.jsp";
	}
	
}
