package com.template.netty.config.redis.configProps;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class RedisConfig {
    @JsonProperty(value = "host")
    private String HOST;

    @JsonProperty(value = "port")
    private Integer PORT;

    @JsonProperty(value = "password")
    private String PASSWORD;
}
