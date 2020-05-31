package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * description springboot整合jedis
 *
 * @author xichengxml
 * @date 2020-05-17 17:22
 */
@RestController
@RequestMapping("/redis")
public class T07_Jedis {

    private static final String KEY = "redis07_key_1";

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @RequestMapping("/setEx")
    public String setEx() {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        shardedJedis.setex(KEY, RedisConstant.EXPIRE_TIME, "1");
        shardedJedis.close();
        return "success";
    }

    @RequestMapping("/get")
    public String get() {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        return shardedJedis.get(KEY);
    }
}
