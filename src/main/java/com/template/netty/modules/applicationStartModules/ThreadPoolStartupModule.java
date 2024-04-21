package com.template.netty.modules.applicationStartModules;

import com.template.netty.modules.threadHandler.request.RequestThreadHandler;
import com.template.netty.modules.threadHandler.response.ResponseThreadHandler;
import com.template.netty.modules.threadHandler.service.ServiceThreadHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThreadPoolStartupModule implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationContext context;

    @Value("${server-setup.thread-count.request}")
    private int requestThreadCount;
    @Value("${server-setup.thread-count.response}")
    private int responseThreadCount;
    @Value("${server-setup.thread-count.service}")
    private int serviceThreadCount;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("### ThreadPoolStartupModule ###");

        log.info("setup ReqeustThreadPool");
        for (int i = 0; i < requestThreadCount; i++) {
            context.getBean(RequestThreadHandler.class).start();
        }
        log.info("setup ServiceThreadPool");
        for (int i = 0; i < serviceThreadCount; i++) {
            context.getBean(ServiceThreadHandler.class).start();
        }
        log.info("setup ResponseThreadPool");
        for (int i = 0; i < responseThreadCount; i++) {
            context.getBean(ResponseThreadHandler.class).start();
        }
    }
}
