package com.luckymiao.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.luckymiao.base.BaseDAO;
import com.luckymiao.common.dao.T01_common_interfaceDAO;
import com.luckymiao.common.dto.T01_common_interface;

@Repository("t01_common_interfaceDAO")
public class T01_common_interfaceDAOImpl extends BaseDAO implements T01_common_interfaceDAO{
	
	public T01_common_interface getT01_common_intefaceByCiid(String ciid) {
		T01_common_interface dto = this.getSqlSession().selectOne("getT01_common_intefaceByCiid", ciid);
		return dto;
	}

}
