package com.anxpp;

import java.io.File;

public class task implements Runnable {
	static int i = 0;
	static String key = "";
	@Override
	public void run() {
		String main = MyExecutor.getNum();
		String s= "http://www." + main + ".com/";
		String content = HtmlUtil.getHtml(s);
		if(content==null)
			return;
		if(content.contains(key)){
			System.err.println("\n" + main);
			new File("D://site/www."+main + ".com");
		}
		else{
			System.out.println("Not："+main);
		}
	}

}
