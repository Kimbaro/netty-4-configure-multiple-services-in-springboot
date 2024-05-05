package com.template.netty.service.impl;

import com.template.netty.db.redis00.entity.IpTableCache;

public interface IpTableService {
    public void findAll();

    public Iterable<IpTableCache> cacheable_findAll();
}
