package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * description 连接单点redis服务
 *
 * @author xichengxml
 * @date 2020-04-22 23:03
 */
@Slf4j
public class T01_Jedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis(RedisConstant.HOST, RedisConstant.PORT01);
        for (int i = 0; i < 10; i++) {
            String key = "redis01_key_" + i;
            String value = "redis01_value" + i;
            jedis.set(key, value);
        }
        Set<String> keys = jedis.keys("redis01*");
        log.info("keys: {}", keys);
        jedis.close();
    }
}
