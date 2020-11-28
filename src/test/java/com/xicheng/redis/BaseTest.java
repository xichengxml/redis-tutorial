package com.xicheng.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-11-28 13:56
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BaseTest {

    @Test
    public void startTest() {
        log.info("Hello, this is springboot test");
    }
}
