package com.luckymiao.wx.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.StringUtils;
import com.luckymiao.wx.dao.T03_shareDAO;
import com.luckymiao.wx.dao.T03_userDAO;
import com.luckymiao.wx.dto.T03_share;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.service.T03_shareService;
@Service("t03_shareService")
public class T03_shareServiceImpl extends BaseService implements T03_shareService{
	@Autowired
	private T03_shareDAO t03_shareDAO;
	@Autowired
	private T03_userDAO t03_userDAO;
	@Override
	public ResponseJson addUserShare(HttpServletRequest request,
			HttpServletResponse response, T03_share t03Share) throws Exception {
		ResponseJson result = new ResponseJson();
		T03_user user = new T03_user();
		user.setTelephone(t03Share.getTelephone());
		T03_user userDb = t03_userDAO.getT03_userByTelephone(user);
		if(userDb != null){
			t03Share.setUid(userDb.getUid());
		}
		t03Share.setCreatedate(DateUtils.getCurrTime());
		t03Share.setSid(StringUtils.getDateNumberSequence());
		t03Share.setIp(request.getRemoteHost());
		t03Share.setBusinesstype("0");
		t03_shareDAO.insertT03_share(t03Share);
		return result;
	}

}
