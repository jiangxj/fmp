package com.luckymiao.wx.dao;

import java.util.List;

import com.luckymiao.base.DAO;
import com.luckymiao.wx.dto.T04_adertising_ticket;
import com.luckymiao.wx.dto.T04_user_ticket;

public interface T04_adertising_ticketDAO extends DAO{

	public List<T04_adertising_ticket> getT04_adertising_ticketListByAd_id(
			T04_adertising_ticket ticket);

	public int modifyT04_adertising_ticketFlagByTid(
			T04_adertising_ticket t04AdertisingTicket);

	public void insertT04_user_ticket(T04_user_ticket userTicket);

	public T04_user_ticket getT04_user_ticketByUid(T04_user_ticket userTicket);

}
