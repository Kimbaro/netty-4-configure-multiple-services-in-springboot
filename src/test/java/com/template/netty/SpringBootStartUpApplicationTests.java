package com.template.netty;

import com.template.netty.config.redis.configProps.RedisConfig;
import com.template.netty.config.redis.configProps.RedisDataSource;
import com.template.netty.db.redis00.entity.IpTableCache;
import com.template.netty.db.redis00.repo.IpTableRepository;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.embedded.RedisServer;

//@SpringBootTest(properties = "classpath:application.yml")
@Slf4j
public class SpringBootStartUpApplicationTests {

    @Autowired
    private RedisDataSource redisDataSource;

    @Autowired
    private IpTableRepository ipTableRepository;

    @Test
    public void test_redisDataSourceManager() {
        //given
        RedisConfig redisConfig = new RedisConfig();

        //when
        redisConfig.setHOST("localhost");
        redisConfig.setPORT(6379);
        redisConfig.setPASSWORD("1234");

        //then
        Assertions.assertThat(redisConfig.getHOST())
                .isEqualTo(redisDataSource.getRedis().get("redis00").getHOST());
        Assertions.assertThat(redisConfig.getPORT())
                .isEqualTo(redisDataSource.getRedis().get("redis00").getPORT());
        Assertions.assertThat(redisConfig.getPASSWORD())
                .isEqualTo(redisDataSource.getRedis().get("redis00").getPASSWORD());
    }

    @Test
    public void test_redisConnectionTest() {
        //given
        RedisURI redisURI = RedisURI.builder()
                .withHost("127.0.0.1")
                .withPort(6380)
                .withDatabase(0)
                .build();

        //when
        RedisClient client = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = client.connect();
        connection.sync().set("hello", "world");
        log.info(connection.sync().get("hello"));

        //then
        Assertions.assertThat("world").isEqualTo(connection.sync().get("hello"));
    }

    @Test
    public void test_redisSave() {
        ipTableRepository.save(IpTableCache.builder().ipTables("127.0.0.1;").build());
    }
}

