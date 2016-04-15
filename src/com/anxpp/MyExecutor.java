package com.anxpp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor {
	
	private final static int cc = 26*26;
	
	private static long i = 777;
	private static long c = 1;
	
	private static char[] s={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; 
	
	public synchronized static String getNum(){
		++c;
		if(c%cc==0){
			++i;
			System.out.print(i);
		}
		long x = c;
		if(c>=cc) x = (int) (c%cc);
		return i+ "" + s[(int) (x/26)] + "" + s[(int) (x%26)];
	}
	
	public static void start(){
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				ExecutorService executorService = Executors.newFixedThreadPool(500);
				while(true){
					executorService.execute(new task());
				}
			}
		});
	}

}
