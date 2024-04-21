package com.template.netty.modules.threadHandler.request;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import com.template.netty.modules.threadHandler.service.ServiceThreadCacheData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;
import static java.lang.Thread.currentThread;


@Slf4j
@Service
@RequiredArgsConstructor
public class RequestThreadHandler {

    private final RequestThreadCacheData requestThreadCacheData;
    private final ResponseThreadCacheData responseThreadCacheData;
    private final ServiceThreadCacheData serviceThreadCacheData;
    @Value("${server-setup.thread-delay.request}")
    private int delay;

    @Async("requestThreadPool")
    public void start() {
        RequestPayload requestPayload = null;
        while (true) {
            try {
                requestPayload = requestThreadCacheData.getRequestPayload();
                if (requestPayload == null) {
                    sleep(delay);
                    continue;
                }
                execute(requestPayload);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void execute(RequestPayload requestPayload){
       log.info("\nREQUEST-THREAD-POOL {} \n| execute \n| CURRENT-CACHE {}", currentThread().getName(), requestPayload);
        serviceThreadCacheData.addServicePayload(requestPayload);
    }
}
