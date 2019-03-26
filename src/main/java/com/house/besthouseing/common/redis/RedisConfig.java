package com.house.besthouseing.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;

/**
 * redis 配置
 * @author 小黑
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.maxTotal}")
    private int maxTotal;

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        //配置连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        ArrayList<JedisShardInfo> arrayList = new ArrayList<>();
        JedisShardInfo shardInfo = new JedisShardInfo(redisProperties.getHost(), redisProperties.getPort());
        if(!"".equals(password) && password!=null){
            shardInfo.setPassword(password);
        }
        arrayList.add(shardInfo);
        return new ShardedJedisPool(jedisPoolConfig, arrayList);
    }

}
