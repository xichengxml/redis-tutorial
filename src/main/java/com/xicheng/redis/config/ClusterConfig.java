package com.xicheng.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-06-15 22:42
 */
@Configuration
public class ClusterConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    @Value("${spring.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.jedis.pool.max-total}")
    private int maxTotal;

    @Value("${spring.jedis.pool.max-wait}")
    private int maxWait;

    private GenericObjectPoolConfig getConfig() {
        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }

    @Bean
    public JedisCluster getCluster() {
        String[] clusterNodeArray = clusterNodes.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        for (String s : clusterNodeArray) {
            String[] hostAndPort = s.split(":");
            hostAndPortSet.add(new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        return new JedisCluster(hostAndPortSet, 1000, getConfig());
    }
}
