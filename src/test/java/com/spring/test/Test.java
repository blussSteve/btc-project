package com.spring.test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.btc.util.HttpClientTool;


public  class Test {
	public static ExecutorService excutor=Executors.newCachedThreadPool(); 
	public static void main(String[] args) {
		test();
	}
	
	public static void test(){
		
		for(int i=0;i<10;i++){
			excutor.execute(new MyThread());
		}
		
	}
	
	static class MyThread implements Runnable{
		@Override
		public void run() {
			String str=HttpClientTool.doGet("http://localhost:8088/test/test1", null);
			System.out.println(str);
		}
		MyThread(){
			super();
		}
	}
	
	
}

