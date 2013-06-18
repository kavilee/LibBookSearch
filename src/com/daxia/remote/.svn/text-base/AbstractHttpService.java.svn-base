package com.daxia.remote;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractHttpService {
	// fields
	private URL url;

	protected HttpURLConnection conn;

	private Map<String, String> textParams = new HashMap<String, String>();

	public AbstractHttpService() {
	}

	public AbstractHttpService(String url) throws IOException {
		this.url = new URL(url);
	}

	public void setUrl(String url) throws IOException {
		this.url = new URL(url);
	}

	/**
	 * 增加一个普通字符串数据到form表单数据中
	 */
	public void addParameter(String name, String value) {
		textParams.put(name, value);
	}

	/**
	 * 初始化Get请求连接
	 * 
	 * @throws IOException
	 */
	protected void initGetConn() throws IOException {
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(5000); // 连接超时为5秒
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded"); // 表单数据
	}

	/**
	 * 初始化Post请求连接
	 * 
	 * @throws Exception
	 */
	protected void initPostConn() throws IOException {
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(5000); // 连接超时为5秒
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded"); // 表单数据
		if (textParams.size() > 0) {
			DataOutputStream outputStream = new DataOutputStream(
					conn.getOutputStream());
			writeStringParams(outputStream);
		}
	}

	/**
	 * 普通字符串数据
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	private void writeStringParams(DataOutputStream outputStream)
			throws IOException {
		StringBuilder content = new StringBuilder();
		Set<String> keySet = textParams.keySet();
		for (String name : keySet) {
			String value = new String(URLEncoder.encode(textParams.get(name),
					"utf-8")); // 使用utf-8传送
			content.append(name + "=" + value);
			content.append("&");
		}
		String post = content.substring(0, content.length() - 1);
		outputStream.writeBytes(post);
	}

	/**
	 * 跳过lineNum
	 * 
	 * @param lineNum
	 * @throws IOException
	 */
	protected void skipLine(BufferedReader dis, int lineNum) throws IOException {
		for (int i = 0; i < lineNum; i++) {
			dis.readLine();
		}
	}

	/**
	 * 截取字符串
	 * 
	 * @param line
	 * @param startKey
	 * @param endKey
	 * @return
	 */
	protected String cutString(String line, String startKey, String endKey) {

		int start = line.indexOf(startKey);
		int end = line.indexOf(endKey);
		if (start == -1 || end == -1) {
			return null;
		}
		String rs = line.substring(start + startKey.length(), end);
		return rs;
	}
}
