package com.template.netty.modules.threadHandler.response;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.dto.netty.ResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.currentThread;

@Slf4j
@Component
public class ResponseThreadCacheData {

    private final HashMap<UUID, ResponsePayload> cacheDataMap = new HashMap<UUID, ResponsePayload>();
    private final List<ResponsePayload> cacheDataList = new ArrayList<ResponsePayload>();
    private final Object monitor = new Object();


    public void addResponsePayload(ResponsePayload responsePayload) {
        synchronized (monitor) {
            this.cacheDataMap.put(responsePayload.getUUID_OBJ(), responsePayload);
            this.cacheDataList.add(responsePayload);
            log.info("\nTHREAD-POOL {} \n| addResponsePayload \n| CURRENT-SIZE {} \n| CURRENT-CACHE {}", currentThread().getName(), cacheDataMap.size(), cacheDataMap);
        }
    }

    public ResponsePayload getResponsePayload() {

        ResponsePayload responsePayload = null;
        synchronized (monitor) {
            if (this.cacheDataList.size() > 0) {
                log.info("\nRESPONSE-THREAD-POOL {} \n| getResponsePayload \n| CURRENT-SIZE {} \n| PULL-CACHE {}", currentThread().getName(), cacheDataMap.size(), responsePayload);
                responsePayload = this.cacheDataList.remove(0);
                this.cacheDataMap.remove(responsePayload.getUUID_OBJ());
            }
        }
        return responsePayload;
    }
}
