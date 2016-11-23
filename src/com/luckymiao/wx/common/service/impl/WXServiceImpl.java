package com.luckymiao.wx.common.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.HttpClientUtils;
import com.luckymiao.wx.common.dto.TextMessage;
import com.luckymiao.wx.common.service.WXService;
import com.luckymiao.wx.dao.Wx_access_tokenDAO;
import com.luckymiao.wx.dto.Wx_access_token;
import com.luckymiao.wx.utils.MessageUtil;

@Service("wXService")
public class WXServiceImpl extends BaseService implements WXService {
	@Autowired
	private Wx_access_tokenDAO wx_access_tokenDAO;
	@Override
	public String handleWXMsg(HttpServletRequest request,
			HttpServletResponse response) {
		String user = (String) request.getAttribute("id");
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "亲 你输入的内容我炸不到  没有做机器人聊天  ！！ ";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String CreateTime = requestMap.get("CreateTime");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				try {
					JSONObject jsonObj = (JSONObject)cacheMap.get(fromUserName);
					if(jsonObj == null){
						Wx_access_token wxAccessToken = new Wx_access_token();
						wxAccessToken.setAppid("wx9c542dd7c5d61186");
						Wx_access_token dbAccess = wx_access_tokenDAO.getWX_jsapi_ticketByAppid(wxAccessToken);
						String jsonData = HttpClientUtils.sendGetStringRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+dbAccess.getAccess_token()+"&openid="+fromUserName+"&lang=zh_CN");
						jsonObj = new JSONObject(jsonData);
						cacheMap.put(fromUserName, jsonObj);
					}
					String from = jsonObj.getString("nickname");
					String headimgurl = jsonObj.getString("headimgurl");
					HttpClientUtils.sendGetRequest("http://139.196.6.63:8888/packet?username="+URLEncoder.encode(from,"utf-8")+"&headimgurl="+URLEncoder.encode(headimgurl,"utf-8")+"&msg="+URLEncoder.encode(requestMap.get("Content"),"utf-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("收到文本消息:"+requestMap.get("Content"));
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				try {
					JSONObject jsonObj = (JSONObject)cacheMap.get(fromUserName);
					if(jsonObj == null){
						Wx_access_token wxAccessToken = new Wx_access_token();
						wxAccessToken.setAppid("wx9c542dd7c5d61186");
						Wx_access_token dbAccess = wx_access_tokenDAO.getWX_jsapi_ticketByAppid(wxAccessToken);
						String jsonData = HttpClientUtils.sendGetStringRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+dbAccess.getAccess_token()+"&openid="+fromUserName+"&lang=zh_CN");
						jsonObj = new JSONObject(jsonData);
						cacheMap.put(fromUserName, jsonObj);
					}
					String from = jsonObj.getString("nickname");
					String headimgurl = jsonObj.getString("headimgurl");
					HttpClientUtils.sendGetRequest("http://139.196.6.63:8888/packet?username="+URLEncoder.encode(from,"utf-8")+"&headimgurl="+URLEncoder.encode(headimgurl,"utf-8")+"&msg="+URLEncoder.encode("<img src='"+requestMap.get("PicUrl")+"'/>","utf-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("收到图文消息："+requestMap.get("PicUrl"));
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				System.out.println("收到文本消息");
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				System.out.println("收到链接消息");
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				System.out.println("收到音频消息");
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "感谢您的关注！！！";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					System.out.println("用户已关注时的事件推送");

					String eventKey = requestMap.get("EventKey");
					if (eventKey.equals("123")) {
						respContent = "用户已关注时的事件推送   事件KEY值》》》" + eventKey;
					}
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}

			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	@Override
	public ResponseJson oauth2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseJson result = new ResponseJson();
		String code = request.getParameter("code");
		String appid = "wx9c542dd7c5d61186";
		String jsonResult = HttpClientUtils.sendGetRequest("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret=da02cffebfb528b81c8fd8ba117aa55f&code="+code+"&grant_type=authorization_code");
		
		JSONObject jsonObjResult = new JSONObject(jsonResult);
		String openid = jsonObjResult.getString("openid");
		Wx_access_token wxAccessToken = new Wx_access_token();
		wxAccessToken.setAppid(appid);
		Wx_access_token dbAccess = wx_access_tokenDAO.getWX_jsapi_ticketByAppid(wxAccessToken);
		String jsonData = HttpClientUtils.sendGetStringRequest("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+dbAccess.getAccess_token()+"&openid="+openid+"&lang=zh_CN");
		result.setData(jsonData);
		return result;
	}

}
