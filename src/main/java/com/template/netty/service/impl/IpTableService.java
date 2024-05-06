package com.template.netty.service.impl;

import com.template.netty.db.redis00.entity.IpTableCache;

import java.util.Map;

public interface IpTableService {

    public Map<String, IpTableCache> findAll();

    public void save(IpTableCache ipTableCache);

    public Map<String, IpTableCache> findAllIptables();

    public IpTableCache findByCompanyId(String companyId);

    public void delete(String companyId);

}
