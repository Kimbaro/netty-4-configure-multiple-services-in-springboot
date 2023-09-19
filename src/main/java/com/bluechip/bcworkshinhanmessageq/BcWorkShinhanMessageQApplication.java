package com.bluechip.bcworkshinhanmessageq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BcWorkShinhanMessageQApplication {
    private int port;

//    @Autowired
//    @Qualifier("fixed_response_thread_pool_executor")
//    ThreadPoolExecutor fixedResponseThreadPoolExecutor;

//    @Autowired
//    @Qualifier("fixed_request_thread_pool_executor")
//    ThreadPoolExecutor fixedRequestThreadPoolExecutor;

    public static void main(String[] args) {
        SpringApplication.run(BcWorkShinhanMessageQApplication.class, args);
//        AbstractApplicationContext context = null;
//        context = new AnnotationConfigApplicationContext(BcWorkShinhanMessageQApplication.class);
//        context.registerShutdownHook();

    }
}

