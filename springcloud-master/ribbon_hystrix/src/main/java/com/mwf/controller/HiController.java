package com.mwf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mwf.service.HiService;

@RestController
public class HiController {
	
	@Autowired
	private HiService hiService;
	
	@RequestMapping("hi")
	public String hi(String name ){
		return hiService.hi(name);
	}
	
	@RequestMapping("timeout")
	public String timeout(){
		return hiService.timeout();
	}
	
	@RequestMapping("exception")
	public String exception(){
		return hiService.exception();
	}
}





