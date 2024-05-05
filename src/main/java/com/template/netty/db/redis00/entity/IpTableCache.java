package com.template.netty.db.redis00.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@RedisHash
public class IpTableCache {
    @Id
    private Long companyId;

    private String ipTables;

    @TimeToLive
    Long ttl;
}
