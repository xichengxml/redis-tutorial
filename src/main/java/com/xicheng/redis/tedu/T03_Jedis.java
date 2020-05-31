package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * description 自定义数据分片的计算
 *
 * @author xichengxml
 * @date 2020-04-22 23:27
 */
@Slf4j
public class T03_Jedis {

    private static final String KEY_PREFIX = "redis03_key_%s";

    private static final String VALUE_PREFIX = "redis03_value_%s";

    public static void main(String[] args) {
        Jedis jedis01 = new Jedis(RedisConstant.HOST, RedisConstant.PORT01);
        Jedis jedis02 = new Jedis(RedisConstant.HOST, RedisConstant.PORT02);
        Jedis jedis03 = new Jedis(RedisConstant.HOST, RedisConstant.PORT03);

        Jedis redisClient = null;
        for (int i = 0; i < 100; i++) {
            if (i <= 33) {
                redisClient = jedis01;
            } else if (i <= 66) {
                redisClient = jedis02;
            } else {
                redisClient = jedis03;
            }
            redisClient.set(String.format(KEY_PREFIX, i), String.format(VALUE_PREFIX, i));
        }

        int i = 69;
        String readKey = String.format(KEY_PREFIX, i);
        String result = redisClient.get(readKey);
        log.info("result: {}", result);
    }
}
