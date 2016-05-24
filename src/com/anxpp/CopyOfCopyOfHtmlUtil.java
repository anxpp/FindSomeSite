package com.anxpp;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class CopyOfCopyOfHtmlUtil {
	static long COUNT = 0;
	static int RAND_NUM = 1;
	static int MIN_INTERVAL = 100;
	public static void main(String args[]) throws Exception{
		ExecutorService executors = Executors.newScheduledThreadPool(10);
		executors.execute(new Task("http://anxpp.com/index.php/archives/865/"));
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
					Thread.sleep(random.nextInt(RAND_NUM)+MIN_INTERVAL);
					getHtml(url);
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
		Random random = new Random(path.hashCode());
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(1200);
		conn.setReadTimeout(2400);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.setRequestProperty("User-Agent", "Mozilla/" + random.nextInt(5) + ".0 (Windows NT " + random.nextInt(10) + ".0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/4" + random.nextInt(9) + ".0.2623.87 Safari/537.36");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0." + random.nextInt(9) + "");
		conn.setRequestProperty("x-forwarded-for", random.nextInt(2) + "" + random.nextInt(9) + "" + random.nextInt(9) + "16" + random.nextInt(9) + "01000" + random.nextInt(9) + "");
		conn.setRequestProperty("ACookie", "__gads=ID=caff5" + random.nextInt(9) + "4ade" + random.nextInt(9) + "0e3f4:T="+ System.currentTimeMillis() +":S=ALNI_MaTy" + random.nextInt(9) + "pW" + random.nextInt(9) + "-uCaJWJNlQxoWo" + random.nextInt(9) + "ZO6QSA;"
				+ " __qca=P0-14" + random.nextInt(9) + "78" + (random.nextInt(89999)+10000) + "-143" + (random.nextInt(89999)+10000) + "" + (random.nextInt(89999)+10000) + "; uuid_tt_dd=" + (random.nextInt(89999)+10000) + "21" + (random.nextInt(89999)+10000) + "95" + (random.nextInt(89999)+10000) + "_20" + (random.nextInt(6)+10) + "0815; bdshare_firstime=1439625028550"+(long)(random.nextInt(99999)+1439525028550.f));
		if (conn.getResponseCode() == 200) {
			count(path);
			InputStream is = conn.getInputStream();
			byte[] data = null;
			data = StreamUtil.read(is);
			conn.connect();
			return new String(data,"gb2312");
		}
		count("ERROR");
		conn.connect();
		return null;
	}
}