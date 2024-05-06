package com.template.netty.service;

import com.template.netty.db.redis00.entity.IpTableCache;
import com.template.netty.service.impl.IpTableService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class IpTableServiceImpl implements IpTableService {
    private final String KEY = "ipTableCache";
    @Qualifier("redis00Template")
    private final RedisTemplate<String, IpTableCache> redisTemplate;
    private HashOperations<String, String, IpTableCache> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, IpTableCache> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public Map<String, IpTableCache> findAllIptables() {
        return hashOperations.entries(KEY);
    }

    @Override
    public IpTableCache findByCompanyId(String companyId) {
        return (IpTableCache) hashOperations.get(KEY, companyId);
    }

    @Override
    public void save(IpTableCache ipTableCache) {
        log.info(ipTableCache.toString());
        hashOperations.putIfAbsent(KEY, ipTableCache.getCompanyId(), ipTableCache);
    }

    @Override
    public void delete(String companyId) {
        hashOperations.delete(KEY, companyId);
    }
}
