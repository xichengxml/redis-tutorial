package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.Arrays;
import java.util.Set;

/**
 * description 使用jedis自带的hash算法完成分片
 *
 * @author xichengxml
 * @date 2020-05-17 16:56
 */
@Slf4j
public class T05_Jedis {

    private static final String KEY_PREFIX = "redis05_key_%s";

    private static final String KEYS_PATTERN = "redis05_key_*";

    public static void main(String[] args) {
        JedisShardInfo jedisShardInfo01 = new JedisShardInfo(RedisConstant.HOST, RedisConstant.PORT01);
        JedisShardInfo jedisShardInfo02 = new JedisShardInfo(RedisConstant.HOST, RedisConstant.PORT02);
        JedisShardInfo jedisShardInfo03 = new JedisShardInfo(RedisConstant.HOST, RedisConstant.PORT03);

        ShardedJedis shardedJedis = new ShardedJedis(Arrays.asList(jedisShardInfo01, jedisShardInfo02, jedisShardInfo03));

        for (int i = 0; i < 1000; i++) {
            String key = String.format(KEY_PREFIX, i);
            shardedJedis.setex(key, RedisConstant.EXPIRE_TIME, i + "");
        }

        Jedis jedis01 = new Jedis(RedisConstant.HOST, RedisConstant.PORT01);
        Jedis jedis02 = new Jedis(RedisConstant.HOST, RedisConstant.PORT02);
        Jedis jedis03 = new Jedis(RedisConstant.HOST, RedisConstant.PORT03);

        Set<String> keys01 = jedis01.keys(KEYS_PATTERN);
        Set<String> keys02 = jedis02.keys(KEYS_PATTERN);
        Set<String> keys03 = jedis03.keys(KEYS_PATTERN);

        log.info("keys01: {}, keys02: {}, keys03: {}", keys01.size(), keys02.size(), keys03.size());
    }
}
