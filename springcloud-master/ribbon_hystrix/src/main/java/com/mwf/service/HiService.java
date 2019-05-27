package com.mwf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
/**
 * Hystrix也支持以注解的形式配置。通过@HystrixCommand注解的fallbackMethod属性指定降级方法。
 * groupKey和commandKey默认为方法名（当然threadPoolKey不指定时，默认和groupKey一致，所以也是方法名），
 * 也可以指定这三个key值，配置文件通过groupKey,commandKey,threadPoolKey使用恰当的配置。
 * commandProperties和threadPoolProperties
 * 是通过@HystrixProperty的name value键值对进行配置。
 * @author 郑仔祥
 *
 */
@Service 
public class HiService {
	@Autowired
	private RestTemplate template;
	@HystrixCommand(fallbackMethod="errorHi")
	public String hi(String name) {
		String response = template.getForObject("http://service-hi/hi1?name="+name, String.class);
		return name+","+response;
	}
	public String errorHi(String name){
		return name+"  error is happened";
	}
	
	/**
	 * 测试超时降级
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "timeoutFallback", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
	    }, commandProperties = {
	            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000")
	    })
    public String timeout() {
        return template.getForObject("http://service-hi/timeout", String.class);
    }

    public String timeoutFallback() {
        return "timeout 降级";
    }
    
    /**
     * 测试异常降级
     * @return
     */
    @HystrixCommand(fallbackMethod = "exceptionFallback", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
    }, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String exception() {
        return template.getForObject("http://service-hi/exception", String.class);
    }

    public String exceptionFallback() {
        return "exception 降级";
    }

	

}
