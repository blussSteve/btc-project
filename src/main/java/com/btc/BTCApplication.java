package com.btc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@MapperScan("com.btc.mapper")
@SpringBootApplication
@EnableScheduling
public class BTCApplication {
	public static void main(String[] args) { 
		
		SpringApplication.run(BTCApplication.class, args);
	}
 
}
						