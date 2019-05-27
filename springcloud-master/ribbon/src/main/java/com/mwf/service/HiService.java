package com.mwf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class HiService {
	@Autowired
	private RestTemplate template;
	
	public String hi(String name) {
		String response = template.getForObject("http://service-hi/hi1?name="+name, String.class);
		return name+","+response;
	}
	
	
}
