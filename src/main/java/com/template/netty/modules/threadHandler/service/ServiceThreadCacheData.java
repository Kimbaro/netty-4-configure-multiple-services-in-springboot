package com.template.netty.modules.threadHandler.service;

import com.template.netty.dto.netty.RequestPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.currentThread;

@Slf4j
@Component
public class ServiceThreadCacheData {

    private final HashMap<UUID, RequestPayload> cacheDataMap = new HashMap<UUID, RequestPayload>();
    private final List<RequestPayload> cacheDataList = new ArrayList<RequestPayload>();
    private final Object monitor = new Object();

    public void addServicePayload(RequestPayload requestPayload) {
        synchronized (monitor) {
            this.cacheDataMap.put(requestPayload.getUUID_OBJ(), requestPayload);
            this.cacheDataList.add(requestPayload);
            log.info("\nTHREAD-POOL {} \n| addServicePayload \n| CURRENT-SIZE {} \n| CURRENT-CACHE {}", currentThread().getName(), cacheDataMap.size(), cacheDataMap);
        }
    }

    public RequestPayload getServicePayload() {
        RequestPayload requestPayload = null;
        synchronized (monitor) {
            if (this.cacheDataList.size() > 0) {
                log.info("\nTHREAD-POOL {} \n| getServicePayload \n| CURRENT-SIZE {} \n| PULL-CACHE {}", currentThread().getName(), cacheDataMap.size(), requestPayload);
                requestPayload = this.cacheDataList.remove(0);
                this.cacheDataMap.remove(requestPayload.getUUID_OBJ());
            }
        }
        return requestPayload;
    }
}
