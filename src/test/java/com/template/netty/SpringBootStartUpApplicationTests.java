package com.template.netty;

import com.template.netty.config.redis.configProps.RedisConfig;
import com.template.netty.config.redis.configProps.RedisDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "classpath:application.yml")
@Slf4j
public class SpringBootStartUpApplicationTests {

    @Autowired
    private RedisDataSource redisDataSource;

    @Test
    public void test_redisDataSourceManager() {
        RedisConfig redisConfig = new RedisConfig();

        redisConfig.setHOST("localhost");
        redisConfig.setPORT(6379);
        redisConfig.setPASSWORD("1234");

        Assertions.assertThat(redisConfig.getHOST())
                .isEqualTo(redisDataSource.getRedis().get("redis00").getHOST());
        Assertions.assertThat(redisConfig.getPORT())
                .isEqualTo(redisDataSource.getRedis().get("redis00").getPORT());
        Assertions.assertThat(redisConfig.getPASSWORD())
                .isEqualTo(redisDataSource.getRedis().get("redis00").getPASSWORD());
    }
}

