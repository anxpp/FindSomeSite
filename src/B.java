import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class B {
	static long COUNT = 0;
	static int RAND_NUM = 5*1000;
	static int MIN_INTERVAL = 2*1000;
	static Random random;
	static List<String> list;
	public static void main(String args[]) throws Exception{
		list = new ArrayList<String>();//C:\java\workspace\web\FindSmallSite\src
//        FileReader reader = new FileReader("urls.txt");
		//http://blog.csdn.net/anxpp/article/details/51485899
        FileReader reader = new FileReader("C:\\java\\workspace\\web\\FindSmallSite\\src\\urls.txt");
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        while((str = br.readLine()) != null) {
        	list.add(str);
        }
        reader.close();
        br.close();
        long reap = System.currentTimeMillis()+new Integer(list.size()).hashCode();
        random = new Random(reap);
        System.out.println("length:"+list.size());
        System.out.println("random:"+RAND_NUM);
        System.out.println("basic MIN_INTERVAL:"+MIN_INTERVAL);
        System.out.println("random basic:"+reap);
		ExecutorService executors = Executors.newSingleThreadExecutor();
		executors.execute(new Task());
	}
	static class Task implements Runnable{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(random.nextInt(RAND_NUM)+MIN_INTERVAL);
					getHtml(list.get(random.nextInt(list.size())));
				} catch (Exception e) {
					System.err.println(e.getMessage());
//					e.printStackTrace();
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
		conn.setConnectTimeout(2000);
		conn.setReadTimeout(5000);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0." + random.nextInt(9) + ",image/webp,*/*;q=0.8");
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
			data = Util.read(is);
			conn.connect();
			return new String(data,"gb2312");
		}
		count("ERROR");
		conn.connect();
		return null;
	}
	static class Util {
		public static byte[] read(InputStream is) throws Exception {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) != -1) {
				out.write(b, 0, len);
			}
			is.close();
			return out.toByteArray();
		}
	}
}