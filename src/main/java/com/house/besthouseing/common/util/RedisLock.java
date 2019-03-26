package com.house.besthouseing.common.util;

import com.house.besthouseing.common.context.Constants;
import com.house.besthouseing.common.exception.ExceptionError;
import com.house.besthouseing.common.redis.service.RedisClientTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

/**
 * redis key锁类
 *
 * @author QJL
 */

public class RedisLock {
	private static Log log = LogFactory.getLog(RedisLock.class);
	//加锁值
	private static final String LOCKED = "TRUE";
	private static final long ONE_MILLI_NANOS = 1000000L;
	//默认超时时间（毫秒）
	private static final long DEFAULT_TIME_OUT = 5000;
	
	private static final Random r = new Random();
	//锁的超时时间（秒），过期删除
	private static final int EXPIRE = 1 * 60;
	
	private RedisClientTemplate redisClientTemplate;
	private String key;
	private boolean locked = false; //锁的状态标识
	
	public RedisLock(RedisClientTemplate redisClientTemplate, String key ){
		this.redisClientTemplate = redisClientTemplate;
		this.key = key;
	}
	
	public void lock(long timeout) throws ExceptionError {
		
		long nano = System.nanoTime();
		timeout *= ONE_MILLI_NANOS;
		
		try {
			while((System.nanoTime()-nano) < timeout){
				if(this.redisClientTemplate.setnx(key, LOCKED) == 1){
					this.redisClientTemplate.expire(key, EXPIRE);
					locked = true;
					break;
					//return locked;
				}else{
					//log.info("本次使用时间："+(System.nanoTime() - nano)+",超时时间："+timeout);
					//短暂休眠，nano避免出现活锁
					Thread.sleep(3,r.nextInt(500));
				}
			}
		} catch (Exception e) {
			log.info("RedisLock.lock 发生了异常");
			e.printStackTrace();
		}
		if(!locked){
			log.info("获取锁失败");
			throw new ExceptionError(Constants.ERROR_TIMEOUT_RESULT, Constants.ERROR_TIMEOUT_MESSAGE);
		}else{
			log.info("获取锁成功，本次使用时间："+(System.nanoTime()-nano)+",超时时间："+timeout);
		}
	}
	
	public void lock() throws ExceptionError {
		lock(DEFAULT_TIME_OUT);
	}
	//无论是否加锁成功。必须调用
	public void unlock(){
		log.info("删除锁");
		if(locked)this.redisClientTemplate.del(key);
	}
}
