package com.xicheng.redis.tedu;

import com.xicheng.redis.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * description 模拟缓存逻辑在系统中的步骤
 *
 * @author xichengxml
 * @date 2020-04-22 23:18
 */
@Slf4j
public class T02_Jedis {

    private static final String KEY = "redis02_key";

    public static void main(String[] args) {
        log.info("用户获取redis02_key的数据");
        Jedis jedis = new Jedis(RedisConstant.HOST, RedisConstant.PORT01);
        String result = null;
        if (jedis.exists(KEY)) {
            result = jedis.get(KEY);
            log.info("从缓存中获取到的数据为: {}", result);
        } else {
            // 模拟从数据库读取的数据
            result = "{'name': 'xichengxml', 'group': '梦境阁'}";
            log.info("从数据库中获取到的数据为: {}", result);
            jedis.set(KEY, result);
        }
    }
}
