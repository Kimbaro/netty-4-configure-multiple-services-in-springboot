package com.template.netty.service;

import com.template.netty.db.redis00.entity.IpTableCache;
import com.template.netty.db.redis00.repo.IpTableRepository;
import com.template.netty.service.impl.IpTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IpTableServiceImpl implements IpTableService {
    private final IpTableRepository ipTableRepository;

    @Override
    public void findAll() {
        ipTableRepository.findAll();
    }

    @Cacheable(value = "IpTableCache", cacheManager = "cacheManager")
    @Override
    public Iterable<IpTableCache> cacheable_findAll() {
        return ipTableRepository.findAll();
    }
}
