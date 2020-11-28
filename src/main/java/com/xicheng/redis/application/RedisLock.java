package com.xicheng.redis.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * description 分布式锁实现
 *
 * @author xichengxml
 * @date 2020-11-28 13:16
 */
@Slf4j
public class RedisLock {

    private RedisTemplate<String, String> redisTemplate;

    private String key;

    private String value;

    public RedisLock() {
    }

    public RedisLock(RedisTemplate<String, String> redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    private static final String UNLOCK_LUA = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]);else return 0;end";

    public boolean lock() {
        value = RandomStringUtils.randomAlphabetic(16);
        try {
            redisTemplate.opsForValue().setIfAbsent(key, value, 60, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.info("RedisLock lock error: ", e);
        }
        return false;
    }

    public void unlockNoException() {
        try {
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_LUA, Long.class);
            redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        } catch (Exception e) {
            log.error("RedisLock unlockNoException error: ", e);
        }
    }
}
