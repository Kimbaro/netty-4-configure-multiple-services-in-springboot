package com.bluechip.bcworkshinhanmessageq.config.threadGroup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.logging.Logger;

@Configuration
public class ThreadManager {


    /*스레드 무제한 생성*/
    @Bean("cached_thread_pool")
    public ExecutorService newCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        return executorService;
    }

    /*제한된 수 만큼 스레드 생성*/
    @Bean("fixed_thread_pool")
    public ExecutorService newFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        return executorService;
    }

    /*제한된 수 만큼 스레드 생성 TEST -------*/
    @Bean("fixed_response_thread_pool_executor")
    public ThreadPoolExecutor fixedResponseThreadPoolExecutor() {
        RejectedExecutionHandler rejectedExecutionHandler = (runnable, threadPoolExecutor) -> {
            Logger.getLogger("Bluechip Thread Manager").info("thread pool exception");

        };

        int CORE_POOL_SIZE = 300;
        int MAXIMUM_POOL_SIZE = 400;
        int KEEP_ALIVE_TIME = 5;

        TimeUnit SECONDS = TimeUnit.SECONDS;
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, SECONDS, new LinkedBlockingQueue<Runnable>(), rejectedExecutionHandler);

        return executorService;
    }

    /*CPU 코어에 비례한 스레드 생성*/
    @Bean("work_thread_pool")
    public ExecutorService newWorkThreadPool() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        return executorService;
    }
}