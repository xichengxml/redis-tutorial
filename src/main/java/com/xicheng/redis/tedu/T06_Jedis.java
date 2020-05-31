package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;

import java.util.Arrays;

/**
 * description jedis连接池
 *
 * @author xichengxml
 * @date 2020-05-17 17:13
 */
@Slf4j
public class T06_Jedis {

    private static final String KEY_PREFIX = "redis06_key_%s";

    public static void main(String[] args) {

        JedisShardInfo jedisShardInfo01 = new JedisShardInfo(RedisConstant.HOST, RedisConstant.PORT01);
        JedisShardInfo jedisShardInfo02 = new JedisShardInfo(RedisConstant.HOST, RedisConstant.PORT02);
        JedisShardInfo jedisShardInfo03 = new JedisShardInfo(RedisConstant.HOST, RedisConstant.PORT03);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxTotal(200);

        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, Arrays.asList(jedisShardInfo01, jedisShardInfo02, jedisShardInfo03));

        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        shardedJedis.setex(String.format(KEY_PREFIX, 1), RedisConstant.EXPIRE_TIME,1 + "");
        shardedJedis.close();
    }
}
