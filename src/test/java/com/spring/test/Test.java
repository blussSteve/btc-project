package com.spring.test;

import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.btc.util.HttpClientTool;


public  class Test {
	public static ExecutorService excutor=Executors.newCachedThreadPool(); 
	public static void main(String[] args) {
//		test1();
//		System.out.println(1^2^1);
		
//		 int[] nums={1,2,3,4,3};
//		 findnumber2(nums);
		
		System.out.println(0^1^0^2^1^3^2^4^3^3^4);
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
	
	public static void test1() {
		  BitSet bitSet=new BitSet();
          int[] nums={1,2,30,4,5,6,5};

          for (int num : nums) {
              if(bitSet.get(num)){
                  System.out.println(num);
                  break;
              }else {
                  bitSet.set(num);
              }
          }
	}
	
	public static void test2(){
		
		 int[] nums={1,2,30,4,5,6,5,7};
		 int re = 1;  
		 for(int k=0;k<nums.length;k++)  
		 {  
		    re = re^nums[k];  
		 }  
		System.out.println(re); 
	}
	private static int findnumber(int[] i) {
		
		int sum = 0;
		for (int j=0;j<i.length;j++){
			 sum=sum+=i[j]-j;
			
		}
		return sum;
		
	}
	
	private static void findnumber2(int[] i) {	
		{  
			
		 int re = 0;  
		 for(int k=0;k<i.length;k++)  
		 {  
		    re = re^i[k];  
		    System.out.println("----"+re);
		    re=re ^k;
		    System.out.println("--"+re);
		  
		 }  
		}  
	}
}

