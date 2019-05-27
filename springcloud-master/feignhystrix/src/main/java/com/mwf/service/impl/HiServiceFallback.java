package com.mwf.service.impl;

import org.springframework.stereotype.Service;

import com.mwf.service.HiService;

@Service
public class HiServiceFallback implements HiService {

	@Override
	public String timeout() {
		return "timeout 降级";
	}

	@Override
	public String exception() {
		return "exception 降级";
	}
	

}
