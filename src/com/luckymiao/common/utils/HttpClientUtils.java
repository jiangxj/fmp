package com.luckymiao.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtils {
	public static String sendPostRequest(String url) {
		return sendPostRequest(url, null);
	}

	public static String sendPostRequest(String url,
			Map<String, String> postParam) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");

		// Create a method instance.
		PostMethod method = new PostMethod(url);

		if (postParam != null && postParam.size() != 0) {
			for (String param : postParam.keySet()) {
				method.setParameter(param, postParam.get(param));
			}
		}

		// Provide custom retry handler is necessary
		// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new
		// DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			long time = System.currentTimeMillis();
			int statusCode = client.executeMethod(method);
			time = System.currentTimeMillis() - time;

			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			return new String(responseBody);

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	public static String sendGetRequest(String url){
		HttpClient client = new HttpClient();
		StringBuilder sb = new StringBuilder();
		InputStream ins = null;
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				ins = method.getResponseBodyAsStream();
				byte[] b = new byte[1024];
				int r_len = 0;
				while ((r_len = ins.read(b)) > 0) {
					sb.append(new String(b, 0, r_len, method.getResponseCharSet()));
				}
			} else {
				System.err.println("Response Code: " + statusCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	public static String sendGetStringRequest(String url){
		HttpClient client = new HttpClient();
		StringBuilder sb = new StringBuilder();
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				sb.append(new String(method.getResponseBody(), "utf-8"));
			} else {
				System.err.println("Response Code: " + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return sb.toString();
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		//oo2aawZlEDJKUouKWaoSiq-WMosY
		//oo2aawYpBseN12H9btzKQIFia5RY
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri="+URLEncode.encode("","utf-8")+"&response_type=code&scope=snsapi_base&state=SOUMONEY#wechat_redirect
		//String str = sendGetStringRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token=6cQaE3sw9JpYAX-BBOwur3yh-ix5N8wysF4tujqLNWog_YpMBMh5nD5979jfDo3fhH9DPUG_mrRllXsU1aKdGFrlHYfTNntkMHYXlaMNux4CQKjABALZT&openid=oo2aawZlEDJKUouKWaoSiq-WMosY&lang=zh_CN");
		//System.out.println(str);
		System.out.println(
				sendGetRequest("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri="+URLEncoder.encode("http://soumoney.net/fmp/wxt/index.html","utf-8")+"&response_type=code&scope=snsapi_base&state=SOUMONEY#wechat_redirect")
		);
	}

}
