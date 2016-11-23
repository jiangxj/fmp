package com.luckymiao.common.utils;

import java.net.URLEncoder;

public class SMSUtils {
	public static String url = "http://222.73.117.158/msg/HttpBatchSendSM?account=shanghai01&pswd=Tch123456";

	public static void sendAuthCodeMessage(String mobile, String authcode)
			throws Exception {
		String nUrl = StringUtils.httpURLParamsJoin(url, "mobile=" + mobile
				+ "&msg=" + URLEncoder.encode("您好，您的验证码是" + authcode, "utf-8")
				+ "&needstatus=true");
		String result = HttpClientUtils.sendGetRequest(nUrl);
	}
	public static void main(String[] args) throws Exception {
		System.out.println(
				HttpClientUtils.sendGetRequest("http://222.73.117.169/msg/HttpBatchSendSM?account=yisouyx&pswd=Yisou1688&needstatus=true&mobile=13261960779&msg="+URLEncoder.encode("尊敬的会员，月标,诚信提现投资1000奖励110,新客户注册并投资1000元18-31天标，得红包奖励50元+利息10元,另外本人返50元,不投资免费领投资奖励60元.活动注册链接：http://www.gujinsuo.com.cn/lejimiao/001.html回复TD退订", "utf-8"))
		);
	}

}
