package com.xicheng.redis.tedu;

import com.xicheng.redis.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * description 哈希取余数据分片计算
 * 哈希取余的计算公式:
 * (key.hashCode() & Integer.MAX_VALUE) % n
 * n表示分片数量
 *
 * @author xichengxml
 * @date 2020-05-13 23:24
 */
@Slf4j
public class T04_Jedis {

    private static final String KEY_PREFIX = "redis04_key_%s";

    private static final String VALUE_PREFIX = "redis04_value_%s";

    public static void main(String[] args) {
        Jedis jedis01 = new Jedis(RedisConstant.HOST, RedisConstant.PORT01);
        Jedis jedis02 = new Jedis(RedisConstant.HOST, RedisConstant.PORT02);
        Jedis jedis03 = new Jedis(RedisConstant.HOST, RedisConstant.PORT03);

        for (int i = 0; i < 1000; i++) {
            String key = String.format(KEY_PREFIX, i);
            String value = String.format(VALUE_PREFIX, i);
            int num = (key.hashCode() & Integer.MAX_VALUE) % 3;
            switch (num) {
                case 0:
                    jedis01.set(key, value);
                    break;
                case 1:
                    jedis02.set(key, value);
                    break;
                case 2:
                    jedis03.set(key, value);
                    break;
                default:
                    throw new RuntimeException("计算结果有误: " + i);
            }
        }
        Set<String> keys01 = jedis01.keys("redis04_key_*");
        Set<String> keys02 = jedis02.keys("redis04_key_*");
        Set<String> keys03 = jedis03.keys("redis04_key_*");
        log.info("total: {}", keys01.size() + keys02.size() + keys03.size());
    }
}
