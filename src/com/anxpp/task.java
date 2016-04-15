package com.anxpp;

import java.io.File;

public class task implements Runnable {
	static int i = 0;
	@Override
	public void run() {
		String main = MyExecutor.getNum();
		String s= "http://www." + main + ".com/";
		String content = HtmlUtil.getHtml(s);
		if(content==null)
			return;
		if(content.contains("亚洲色图")){
			System.err.println("\n" + main);
			new File("D://site/www."+main + ".com");
		}
		else{
			System.out.println("Not："+main);
		}
	}

}
