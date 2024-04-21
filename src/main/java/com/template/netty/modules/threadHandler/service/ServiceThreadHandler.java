package com.template.netty.modules.threadHandler.service;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.model.ModelTemplate;
import com.template.netty.modules.threadHandler.request.RequestThreadCacheData;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceThreadHandler {

    private final RequestThreadCacheData requestThreadCacheData;
    private final ResponseThreadCacheData responseThreadCacheData;
    private final ServiceThreadCacheData serviceThreadCacheData;
    private final ApplicationContext context;

    @Value("${server-setup.thread-delay.service}")
    private int delay;

    @Async("serviceThreadPool")
    public void start() {
        RequestPayload requestPayload = null;
        while (true) {
            try {
                requestPayload = serviceThreadCacheData.getServicePayload();
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

    private void execute(RequestPayload requestPayload) {
        log.info("\nSERVICE-THREAD-POOL {} \n| execute \n| CURRENT-CACHE {}", currentThread().getName(), requestPayload);
        ModelTemplate modelTemplate = null;
        if (requestPayload.getFlag() == null) {
            //router
            modelTemplate = (ModelTemplate) this.context.getBean("BasicRouter");
        } else {
            modelTemplate = (ModelTemplate) this.context.getBean("Service_" + requestPayload.getFlag());
        }
        modelTemplate.execute(requestPayload);
    }
}
