package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import com.xicheng.redis.tedu.common.SentinelConstant;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * description 通过哨兵集群连接redis
 *
 * @author xichengxml
 * @date 2020-05-31 16:40
 */
@Slf4j
public class T08_Sentinel {

    private static final String KEY = "sentinel08_key";

    public static void main(String[] args) {
        String host = SentinelConstant.HOST;
        int port01 = SentinelConstant.PORT01;
        int port02 = SentinelConstant.PORT02;
        // 多个哨兵之间互相没有监控关系，所以采取遍历的方式获取redis连接
        Set<String> sentinelSet = new HashSet<>();
        sentinelSet.add(new HostAndPort(host, port01).toString());
        sentinelSet.add(new HostAndPort(host, port02).toString());

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(SentinelConstant.REDIS_CLUSTER_NAME, sentinelSet);
        log.info("current redis master: {}", jedisSentinelPool.getCurrentHostMaster());
        Jedis jedis = jedisSentinelPool.getResource();

        jedis.setex(KEY, RedisConstant.EXPIRE_TIME, "xichengxml");
        log.info("value: {}. expire time: {}", jedis.get(KEY), jedis.ttl(KEY));

        jedis.close();
        jedisSentinelPool.destroy();
        log.info("finish.........................");
    }
}
