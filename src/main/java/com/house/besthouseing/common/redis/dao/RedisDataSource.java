package com.house.besthouseing.common.redis.dao;

import redis.clients.jedis.ShardedJedis;

/**
 * 获取redis连接接口
 * @author QJL
 *
 */
public interface RedisDataSource {

	/**
	 * 获取链接
	 * @return
	 */
	public ShardedJedis getRedisClient();
	/**
	 * 归还连接
	 * @param shardedJedis
	 */
	public void returnResource(ShardedJedis shardedJedis);
	public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
