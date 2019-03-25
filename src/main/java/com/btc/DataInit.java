package com.btc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.btc.service.RedisService;

@Component
@Order(4)
public class DataInit implements CommandLineRunner{
	
	@Autowired
	private RedisService redisService;
	@Override
	public void run(String... args) throws Exception {
	}
}
