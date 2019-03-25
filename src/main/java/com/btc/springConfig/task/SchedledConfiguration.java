package com.btc.springConfig.task;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class SchedledConfiguration {
    @Bean  
    public TaskScheduler poolScheduler() {  
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();  
        scheduler.setThreadNamePrefix("poolScheduler");  
        scheduler.setPoolSize(10);  
        return scheduler;  
    }  
}
