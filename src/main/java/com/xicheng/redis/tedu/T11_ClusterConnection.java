package com.xicheng.redis.tedu;

import com.xicheng.redis.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

/**
 * description Springboot整合集群
 *
 * @author xichengxml
 * @date 2020-06-15 22:41
 */
@Slf4j
@RestController
@RequestMapping("/jedis11")
public class T11_ClusterConnection {

    @Autowired
    private JedisCluster jedisCluster;

    private static final String KEY = "jedis11_key";

    private static final String VALUE = "jedis11_value";

    @RequestMapping("/setEx")
    public String setEx() {
        jedisCluster.setex(KEY, RedisConstant.EXPIRE_TIME, VALUE);
        return "success";
    }

    @RequestMapping("/get")
    public String get() {
        return jedisCluster.get(KEY);
    }
}
