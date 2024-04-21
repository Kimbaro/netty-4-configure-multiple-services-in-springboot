package com.template.netty.modules.threadHandler.request;

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
public class RequestThreadCacheData {
    private final HashMap<UUID, RequestPayload> cacheDataMap = new HashMap<UUID, RequestPayload>();
    private final List<RequestPayload> cacheDataList = new ArrayList<RequestPayload>();
    private final Object monitor = new Object();

    public void addRequestPayload(RequestPayload requestPayload) {
        synchronized (monitor) {
            this.cacheDataMap.put(requestPayload.getUUID_OBJ(), requestPayload);
            this.cacheDataList.add(requestPayload);
            log.info("\nTHREAD-POOL {} \n| addRequestPayload \n| CURRENT-SIZE {} \n| CURRENT-CACHE {}", currentThread().getName(), cacheDataMap.size(), cacheDataMap);
        }
    }

    public RequestPayload getRequestPayload() {
        RequestPayload requestPayload = null;
        synchronized (monitor) {
            if (this.cacheDataList.size() > 0) {
                requestPayload = this.cacheDataList.remove(0);
                this.cacheDataMap.remove(requestPayload.getUUID_OBJ());
            }
            log.info("\nTHREAD-POOL {} \n| getRequestPayload \n| CURRENT-SIZE {} \n| PULL-CACHE {}", currentThread().getName(), cacheDataMap.size(), requestPayload);
        }
        return requestPayload;
    }
}
