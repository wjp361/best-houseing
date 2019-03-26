package com.house.besthouseing.common.util;

import com.house.besthouseing.common.redis.service.RedisClientTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class BeanUtil implements ApplicationContextAware {

	public static RedisClientTemplate redisClientTemplate;
	private static ApplicationContext acxt ;

	@Override
	public void setApplicationContext(ApplicationContext cxt) throws BeansException {
		acxt = cxt;
	}
	
	public static Object getBean(String beanName){
		if(acxt == null){
			return null;
		}
		return acxt.getBean(beanName);
	}
	public void setRedisClientTemplate(RedisClientTemplate redisClientTemplate){
		BeanUtil.redisClientTemplate = redisClientTemplate;
	}
	
}
