package com.house.besthouseing.common.redis.dao.impl;

import com.house.besthouseing.common.redis.dao.RedisDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Repository
public class RedisDataSourceImpl implements RedisDataSource {

	private static final Log log = LogFactory.getLog(RedisDataSourceImpl.class);
	
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	@Override
	public ShardedJedis getRedisClient() {
		try {
			ShardedJedis shardedJedis = this.shardedJedisPool.getResource();
			return shardedJedis;
		} catch (Exception e) {
			log.error("getRedisClient",e);
		}
		return null;
	}

	@Override
	public void returnResource(ShardedJedis shardedJedis) {
		shardedJedisPool.close();
	}

	@Override
	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
		if(broken){
			shardedJedisPool.close();
		}else{
			shardedJedisPool.close();
		}
	}

}
