package com.luckymiao.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luckymiao.base.Service;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T04_adertising_ticket;

public interface T04_adertising_ticketService extends Service{

	public ResponseJson getTicket(HttpServletRequest request,
			HttpServletResponse response, T04_adertising_ticket ticket) throws Exception;

}
