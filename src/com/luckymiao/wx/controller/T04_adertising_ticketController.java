package com.luckymiao.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.wx.dto.T04_adertising_ticket;
import com.luckymiao.wx.service.T04_adertising_ticketService;

@Controller
@RequestMapping("/adertising")
public class T04_adertising_ticketController {
	@Autowired
	private T04_adertising_ticketService t04_adertising_ticketService;
	@ResponseBody
	@RequestMapping("/ticket")
	public String getTicket(HttpServletRequest request,
			HttpServletResponse response, T04_adertising_ticket ticket){
		ResponseJson jsonObj = new ResponseJson();
		try {
			jsonObj = t04_adertising_ticketService.getTicket(request, response, ticket);
			jsonObj.setStatusSuccess();
		} catch (Exception e) {
			jsonObj.setStatusError();
		}
		return jsonObj.toString();
	}
}
