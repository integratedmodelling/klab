package org.integratedmodelling.klab.engine.configs;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
    
    @Override
    public Executor getAsyncExecutor() {
         ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
         threadPool.setCorePoolSize(2);
         threadPool.setMaxPoolSize(2);
         threadPool.setQueueCapacity(500);
         threadPool.setThreadNamePrefix("stat-server-post");
         threadPool.initialize();
         return threadPool;
    }
    
}