package com.xicheng.redis.tedu;

import com.xicheng.redis.tedu.common.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * description 集群的连接
 *
 * @author xichengxml
 * @date 2020-06-15 22:11
 */
@Slf4j
public class T10_ClusterConnection {

    private static final String KEY_PREFIX = "redis10_key_%s";

    private static final String VALUE_PREFIX = "redis10_value_%s";

    public static void main(String[] args) {
        // 收集节点信息，整个集群，至少提供一个节点信息
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        hostAndPortSet.add(new HostAndPort(RedisConstant.CLUSTER_HOST, RedisConstant.CLUSTER_PORT01));

        // 配置对象
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(8);
        genericObjectPoolConfig.setMaxTotal(16);

        // 创建集群对象，不需要分片计算
        JedisCluster jedisCluster = new JedisCluster(hostAndPortSet, 1000, genericObjectPoolConfig);

        for (int i = 0; i < 1000; i++) {
            jedisCluster.setex(String.format(KEY_PREFIX, i), RedisConstant.EXPIRE_TIME, String.format(VALUE_PREFIX, i));
        }
        String result = jedisCluster.get("redis10_key_1");
        log.info("result: {}", result);
    }
}
