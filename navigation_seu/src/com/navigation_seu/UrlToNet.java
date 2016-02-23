package com.navigation_seu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UrlToNet {

	static String geturlAddress = "http://192.16.137.1/aa/getdata.php";
	static String posturlAddress = "http://192.16.137.1/aa/postdata.php";
	static URL url;
	static HttpURLConnection uRLConnection;
	String response = "";
	postdata postresponse;
	getdata getresponse;
	List<postdata> postresponse1;

	public interface DataCallback {
		public void process(getdata getresponse);
	}

	public interface DataCallback1 {
		public void process(postdata postresponse);
	}

	public static void doGet(final String id, final DataCallback datacallback) {
		new Thread(new Runnable() {
			public void run() {
				String getUrl = geturlAddress + "?id=" + id + "&judge=1";
				try {
					URL url = new URL(getUrl);
					HttpURLConnection uRLConnection = (HttpURLConnection) url
							.openConnection();
					InputStream is = uRLConnection.getInputStream();
					StringBuffer buffer = new StringBuffer();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String readLine = null;
					while ((readLine = br.readLine()) != null) {
						buffer.append(readLine);
					}
					is.close();
					br.close();
					uRLConnection.disconnect();
					Gson gson = new Gson();
					String response = buffer.toString();
					if (response == null || response == "") {
						datacallback.process(null);

					} else {
						getdata getresponse = gson.fromJson(buffer.toString(),
								new TypeToken<getdata>() {
								}.getType());
						datacallback.process(getresponse);
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					datacallback.process(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					datacallback.process(null);
				}
			}
		}).start();
	}

	public static void doPost(final String id, final String lat,
			final String lon, final DataCallback1 dataCallback1) {

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					url = new URL(posturlAddress);
					uRLConnection = (HttpURLConnection) url.openConnection();
					uRLConnection.setDoInput(true);
					uRLConnection.setDoOutput(true);
					uRLConnection.setRequestMethod("POST");
					uRLConnection.setUseCaches(false);
					uRLConnection.setInstanceFollowRedirects(false);
					uRLConnection.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					uRLConnection.connect();

					DataOutputStream out = new DataOutputStream(
							uRLConnection.getOutputStream());
					String content = "id=" + id + "&lat=" + lat + "&lng=" + lon
							+ "&judge=1";
					out.writeBytes(content);
					out.flush();
					out.close();

					InputStream is = uRLConnection.getInputStream();
					StringBuffer buffer = new StringBuffer();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));

					String readLine = null;
					while ((readLine = br.readLine()) != null) {
						// response = br.readLine();
						buffer.append(readLine);
					}
					String response = buffer.toString();
					is.close();
					br.close();
					uRLConnection.disconnect();

//					Gson gson = new Gson();
//
//					if (response == null || response == "") {
//						dataCallback1.process(null);
//
//					} else {
//						postdata postresponse = gson.fromJson(
//								buffer.toString(), new TypeToken<postdata>() {
//								}.getType());
//						dataCallback1.process(postresponse);
//					}

				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					dataCallback1.process(null);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					dataCallback1.process(null);
				}
				super.run();
			}
		}.start();

	}

}
