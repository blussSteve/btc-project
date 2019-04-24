//package com.btc.util;
//
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.Trigger;
//import org.springframework.scheduling.TriggerContext;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SpringDynamicCronTask implements SchedulingConfigurer {
//
// 
//
//    private static final Logger logger = LoggerFactory.getLogger(SpringDynamicCronTask.class);
//
// 
//
//    private static String cron = "0/5 * * * * ?";
//
// 
//
// 
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//    	
//    	
//    	scheduledTaskRegistrar.addTriggerTask(new Runnable() {
//			@Override
//			public void run() {
//				logger.error("dynamicCronTask is running...");
//				
//			}
//		},new Trigger() {
//			@Override
//			public Date nextExecutionTime(TriggerContext triggerContext) {
//				 CronTrigger cronTrigger = new CronTrigger(cron);
//		          return cronTrigger.nextExecutionTime(triggerContext);
//			}
//		});
//
//    }
//
//
//    public SpringDynamicCronTask() {
//    	
//    	//模拟业务修改周期,可以在具体业务中修改参数cron
//    	new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				  try {
//
//		                Thread.sleep(15000);
//
//		            } catch (InterruptedException e) {
//
//		                e.printStackTrace();
//
//		            }
//
//		            cron = "0/2 * * * * ?";
//				
//			}
//		});
//
//
//    }
//
// 
//
//}