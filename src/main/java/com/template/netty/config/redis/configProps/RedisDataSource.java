package com.template.netty.config.redis.configProps;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasources.nosql")
@Builder
public class RedisDataSource {

    private Map<String, RedisConfig> redis;
}
