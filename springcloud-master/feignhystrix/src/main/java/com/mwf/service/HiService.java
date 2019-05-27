package com.mwf.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mwf.service.impl.HiServiceFallback;

@FeignClient(name="service-hi",fallback=HiServiceFallback.class)
public interface HiService {
	
	@RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout();

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public String exception();
	
}
