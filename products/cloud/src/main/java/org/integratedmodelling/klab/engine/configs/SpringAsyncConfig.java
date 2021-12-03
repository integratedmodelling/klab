package org.integratedmodelling.klab.engine.configs;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class SpringAsyncConfig implements AsyncConfigurer {
    
    @Override
    public Executor getAsyncExecutor() {
         ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
         threadPool.setCorePoolSize(4);
         threadPool.setMaxPoolSize(50);
         threadPool.setQueueCapacity(500);
         threadPool.setThreadNamePrefix("remote-thread-execute");
         threadPool.initialize();
         return threadPool;
    }
    
}