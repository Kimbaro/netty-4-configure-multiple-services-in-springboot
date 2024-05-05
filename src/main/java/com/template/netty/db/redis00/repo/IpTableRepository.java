package com.template.netty.db.redis00.repo;

import com.template.netty.db.redis00.entity.IpTableCache;
import org.springframework.data.repository.CrudRepository;

public interface IpTableRepository extends CrudRepository<IpTableCache, Long> {
}
