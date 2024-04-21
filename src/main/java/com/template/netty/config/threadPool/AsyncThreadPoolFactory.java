package com.template.netty.config.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncThreadPoolFactory {

    private static int TASK_CORE_POOL_SIZE = 50;   // Minimum parallel threads can run at the same time
    private static int TASK_MAX_POOL_SIZE = 200;    // Maximum parallel threads can run at the same time
    private static int TASK_QUEUE_CAPACITY = 500;   // Queue is using when all core pool are filled

    @Bean(name="requestThreadPool")
    public Executor requestThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setThreadNamePrefix("requestThread-async-");
        // The thread invokes itself on rejected pool. You will not lose the thread.
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }

    @Bean(name="responseThreadPool")
    public Executor responseThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setThreadNamePrefix("requestThread-async-");
        // The thread invokes itself on rejected pool. You will not lose the thread.
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }

    @Bean(name="serviceThreadPool")
    public Executor serviceThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setThreadNamePrefix("requestThread-async-");
        // The thread invokes itself on rejected pool. You will not lose the thread.
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }
}
