package com.luckymiao.wx.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.wx.dao.T04_adertising_ticketDAO;
import com.luckymiao.wx.dto.T04_adertising_ticket;
import com.luckymiao.wx.dto.T04_user_ticket;
@Repository("t04_adertising_ticketDAO")
public class T04_adertising_ticketDAOImpl extends BaseDAO implements T04_adertising_ticketDAO{

	@Override
	public List<T04_adertising_ticket> getT04_adertising_ticketListByAd_id(
			T04_adertising_ticket ticket) {
		return this.getSqlSession().selectList("getT04_adertising_ticketListByAd_id", ticket);
	}

	@Override
	public int modifyT04_adertising_ticketFlagByTid(
			T04_adertising_ticket t04AdertisingTicket) {
		return this.getSqlSession().update("modifyT04_adertising_ticketFlagByTid", t04AdertisingTicket);
	}

	@Override
	public void insertT04_user_ticket(T04_user_ticket userTicket) {
		this.getSqlSession().insert("insertT04_user_ticket", userTicket);
	}

	@Override
	public T04_user_ticket getT04_user_ticketByUid(T04_user_ticket userTicket) {
		return this.getSqlSession().selectOne("getT04_user_ticketByUid", userTicket);
	}

}
