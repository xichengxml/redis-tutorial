package com.xicheng.redis.application;

import com.xicheng.redis.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-11-28 14:01
 */
@Slf4j
public class RedisLockTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String LOCK_KEY = "key";

    @Test
    public void lockTest() {
        RedisLock redisLock = new RedisLock(redisTemplate, LOCK_KEY);
        redisLock.lock();
        String value = redisTemplate.opsForValue().get(LOCK_KEY);
        log.info("RedisLockTest lockTest value: {}", value);
    }

    @Test
    public void unlockTest() {
        RedisLock redisLock = new RedisLock(redisTemplate, LOCK_KEY);
        redisLock.lock();
        String value = redisTemplate.opsForValue().get(LOCK_KEY);
        log.info("RedisLockTest unlockTest value lock: {}", value);
        redisLock.unlockNoException();
        // 值为空
        value = redisTemplate.opsForValue().get(LOCK_KEY);
        log.info("RedisLockTest unlockTest value unlock: {}", value);
    }

    /**
     * 测试多线程下的误解锁，用线程2去解锁线程1的锁，不应该成功
     * 可以通过不同的RedisLock对象来模拟
     */
    @Test
    public void concurrentUnlockTest() {
        RedisLock redisLock01 = new RedisLock(redisTemplate, LOCK_KEY);
        redisLock01.lock();
        String value = redisTemplate.opsForValue().get(LOCK_KEY);
        log.info("RedisLockTest concurrentUnlockTest value redisLock01: {}", value);
        RedisLock redisLock02 = new RedisLock(redisTemplate, LOCK_KEY);
        redisLock02.lock();
        // key 过期时间60秒，此时还未过期
        redisLock02.unlockNoException();
        // 值非空
        value = redisTemplate.opsForValue().get(LOCK_KEY);
        log.info("RedisLockTest concurrentUnlockTest value unlock: {}", value);
    }
}
