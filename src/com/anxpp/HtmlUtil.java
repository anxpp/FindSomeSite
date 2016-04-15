package com.anxpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HtmlUtil {
	public static String getHtml(String path){
		//创建连接
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(path).openConnection();
		} catch (IOException e) {
			System.out.println("ERROR：" + path + "打开错误 = " + e.getMessage());
			return null;
		}
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.out.println("ERROR：" + path + "请求错误 = " + e.getMessage());
			return null;
		}
		conn.setConnectTimeout(1200);
		conn.setReadTimeout(2400);
		try {
			if (conn.getResponseCode() == 200) {
				//获取输入流
				InputStream is = conn.getInputStream();
				//通过StreamTool解析输入流得到byte[]数组
				byte[] data = null;
				data = StreamUtil.read(is);
				//返回解析后的脚本代码
				return new String(data,"gb2312");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("ERROR：" + path + "编码错误 = " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR：" + path + "读写错误 = " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ERROR：" + path + "其他错误 = " + e.getMessage());
		}
		return null;
	}
}