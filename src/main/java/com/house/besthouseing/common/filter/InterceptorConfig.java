package com.house.besthouseing.common.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	private static final Log log = LogFactory.getLog(InterceptorConfig.class);
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		log.info("----------------启动拦截器----------------");
		registry.addInterceptor(new ClockInterceptor());
	}
}
