package com.anxpp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyOfHtmlUtil {
	static long COUNT = 0;
	static int RAND_NUM = 20000;
	static int MIN_INTERVAL = 1000;
	public static void main(String args[]) throws Exception{
		ExecutorService executors = Executors.newScheduledThreadPool(10);
		executors.execute(new Task("http://blog.csdn.net/anxpp/article/details/51415698"));
		executors.execute(new Task("http://blog.csdn.net/anxpp/article/details/51415366"));
		executors.execute(new Task("http://blog.csdn.net/anxpp/article/details/51295020"));
		executors.execute(new Task("http://blog.csdn.net/anxpp/article/details/51325838"));
	}
	static class Task implements Runnable{
		String url;
		public Task(String url) {
			this.url = url;
		}
		@Override
		public void run() {
			Random random = new Random(url.hashCode());
			while(true){
				try {
					getHtml(url);
					Thread.sleep(random.nextInt(RAND_NUM)+MIN_INTERVAL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static synchronized void count(String id){
		System.out.println(++COUNT + ":" + new Time(System.currentTimeMillis()) + ":" + id);
	}
	public static String getHtml(String path) throws Exception{
		//创建连接
		Random random = new Random(path.hashCode());
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(1200);
		conn.setReadTimeout(2400);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT " + random.nextInt(10) + ".0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/4" + random.nextInt(9) + ".0.2623.87 Safari/537.36");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0." + random.nextInt(9) + "");
		conn.setRequestProperty("x-forwarded-for", random.nextInt(2) + "" + random.nextInt(9) + "" + random.nextInt(9) + "168010005");
		conn.setRequestProperty("ACookie", "__gads=ID=caff5" + random.nextInt(9) + "4ade" + random.nextInt(9) + "0e3f4:T="+ System.currentTimeMillis() +":S=ALNI_MaTy2pW" + random.nextInt(9) + "-uCaJWJNlQxoWo" + random.nextInt(9) + "ZO6QSA;"
				+ " __qca=P0-14378" + (random.nextInt(89999)+10000) + "-14396239" + (random.nextInt(89999)+10000) + "; uuid_tt_dd=" + (random.nextInt(89999)+10000) + "21" + (random.nextInt(89999)+10000) + "95" + (random.nextInt(89999)+10000) + "_20" + (random.nextInt(6)+10) + "0815; bdshare_firstime=1439625028550"+(long)(random.nextInt(99999)+1439525028550.f));
		if (conn.getResponseCode() == 200) {
			count(path);
			//获取输入流
			InputStream is = conn.getInputStream();
			//通过StreamTool解析输入流得到byte[]数组
			byte[] data = null;
			data = StreamUtil.read(is);
			conn.connect();
			//返回解析后的脚本代码
			return new String(data,"gb2312");
		}
		count("ERROR");
		conn.connect();
		return null;
	}
}