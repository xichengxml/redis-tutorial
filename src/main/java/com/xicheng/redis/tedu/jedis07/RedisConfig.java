package com.xicheng.redis.tedu.jedis07;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-05-17 17:27
 */
@Configuration
public class RedisConfig {

    @Value("${spring.jedis.pool.nodes}")
    private String nodes;

    @Value("${spring.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.jedis.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.jedis.pool.max-total}")
    private Integer maxTotal;

    @Value("${spring.jedis.pool.max-wait}")
    private Integer maxWait;

    private JedisPoolConfig getConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        return jedisPoolConfig;
    }

    @Bean
    public ShardedJedisPool getPool() {
        return new ShardedJedisPool(getConfig(), getJedisShardList());
    }

    private List<JedisShardInfo> getJedisShardList() {
        ArrayList<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        String[] nodeList = nodes.split(",");
        for (String node : nodeList) {
            String[] hostAndPort = node.split(":");
            String host = hostAndPort[0];
            String port = hostAndPort[1];
            JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
            jedisShardInfoList.add(jedisShardInfo);
        }
        return jedisShardInfoList;
    }
}
