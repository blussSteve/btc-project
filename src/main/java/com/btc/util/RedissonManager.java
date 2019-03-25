package com.btc.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RedissonManager {

	private final static Logger logger=LoggerFactory.getLogger(RedissonManager.class);
	private static final String LOCK_TITLE = "redisLock_";
	private static final String RAtomicName = "genId_";

    private static Config config = new Config();
    private static RedissonClient redisson = null;

    
    @Value("${spring.redis.host}")
    private String redishost;
    
    @Value("${spring.redis.port}")
    private String redisport;

    
    @PostConstruct 
    public void init(){
        try {
        	
        	//指定编码，默认编码为org.redisson.codec.JsonJacksonCodec
        	//之前使用的spring-data-redis，用的客户端jedis，编码为org.springframework.data.redis.serializer.StringRedisSerializer
        	//改用redisson后为了之间数据能兼容，这里修改编码为org.redisson.client.codec.StringCodec
        	config.setCodec(new org.redisson.client.codec.StringCodec()).
        	useSingleServer().setAddress("redis://"+redishost+":"+redisport).//指定使用单节点部署方式
        	 setConnectionPoolSize(300).//设置对于master节点的连接池中连接数最大为500
        	 setIdleConnectionTimeout(10000).//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
        	 setConnectTimeout(30000).//同任何节点建立连接时的等待超时。时间单位是毫秒。
        	 setTimeout(3000).//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
        	 setPingTimeout(30000).
        	 setReconnectionTimeout(3000);//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
            redisson = Redisson.create(config);
            //清空自增的ID数字
            RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);
            atomicLong.set(1);
        }catch (Exception e){
            logger.error(""+e);
        }
    }
    public static RedissonClient redisson(){
    	return redisson;
    }

    /** 获取redis中的原子ID */
    public static Long nextId(){
        RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }
    
    
//    public static boolean lock(long leaseTime,TimeUnit timeUnit,Object... lockNames){
//    	StringBuffer sb=new StringBuffer();
//    	for(Object obj:lockNames){
//    		sb.append(obj).append(":");
//    	}
//    	return lock(sb.toString());
//    }
    public static boolean lock(String lockName){
    	 String key = LOCK_TITLE + lockName;
         RLock mylock = redisson.getLock(key);
         logger.info("======lock======",Thread.currentThread().getName());
         try {
        	 return mylock.tryLock(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(""+e);
		}
         return false;
         
    }

    public static boolean lock(String lockName,long leaseTime,TimeUnit timeUnit){
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        logger.info(">>>>>>>>>>> lock: {}",Thread.currentThread().getName());
        try {
            return mylock.tryLock(10, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            logger.error(""+e);
        }
        return false;

    }
//    public static void unlock(Object... lockNames){
//    	StringBuffer sb=new StringBuffer();
//    	for(Object obj:lockNames){
//    		sb.append(obj).append(":");
//    	}
//    	unlock(sb.toString());
//    }
    public static void unlock(String lockName){
    	 String key = LOCK_TITLE + lockName;
         RLock mylock = redisson.getLock(key);
         mylock.unlock();
        logger.info("<<<<<<<<<<< unlock: {}",Thread.currentThread().getName());
    }
}
