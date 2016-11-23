package com.luckymiao.wx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.StringUtils;
import com.luckymiao.wx.dao.T03_userDAO;
import com.luckymiao.wx.dao.T04_adertising_ticketDAO;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T04_adertising_ticket;
import com.luckymiao.wx.dto.T04_user_ticket;
import com.luckymiao.wx.service.T04_adertising_ticketService;
@Service("t04_adertising_ticketService")
public class T04_adertising_ticketServiceImpl extends BaseService implements T04_adertising_ticketService{
	@Autowired
	private T04_adertising_ticketDAO t04_adertising_ticketDAO;
	@Autowired
	private T03_userDAO t03_userDAO;
	@Override
	public ResponseJson getTicket(HttpServletRequest request,
			HttpServletResponse response, T04_adertising_ticket ticket)
			throws Exception {
		ResponseJson result = new ResponseJson();
		T03_user userParam = new T03_user();
		userParam.setTelephone(request.getParameter("telephone"));
		T03_user dbUser = t03_userDAO.getT03_userByTelephone(userParam);
		if(dbUser != null && !"".equals(dbUser.getUid())){
			T04_user_ticket ticketParam = new T04_user_ticket();
			ticketParam.setUid(dbUser.getUid());
			T04_user_ticket db_user_ticket = t04_adertising_ticketDAO.getT04_user_ticketByUid(ticketParam);
			if(db_user_ticket == null || "".equals(db_user_ticket.getHid())){
				List<T04_adertising_ticket> list = t04_adertising_ticketDAO.getT04_adertising_ticketListByAd_id(ticket);
				boolean flag = false;
				for (T04_adertising_ticket t04AdertisingTicket : list) {
					int i = t04_adertising_ticketDAO.modifyT04_adertising_ticketFlagByTid(t04AdertisingTicket);
					if(i == 1){
						String AdTicket = t04AdertisingTicket.getTicket();
						T04_user_ticket user_ticket = new T04_user_ticket();
						user_ticket.setHid(StringUtils.getDateNumberSequence());
						user_ticket.setTid(t04AdertisingTicket.getTid());
						user_ticket.setUid(dbUser.getUid());
						user_ticket.setCreatedate(DateUtils.getCurrTime());
						t04_adertising_ticketDAO.insertT04_user_ticket(user_ticket);
						Map dataMap = new HashMap();
						dataMap.put("ticket", AdTicket);
						result.setData(dataMap);
						result.setCode("1");
						result.setCodemsg("领取成功");
						flag = true;
						break;
					}
				}
				if(!flag){
					result.setCode("0");
					result.setCodemsg("领取完了");
				}
			}else{
				result.setCode("3");
				result.setCodemsg("该用户已经领取过了，不能重复领取！");
			}
			
		}else{
			result.setCode("2");
			result.setCodemsg("非法访问");
		}
		return result;
	}

}
