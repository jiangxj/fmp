package com.luckymiao.common.dao;

import com.luckymiao.base.DAO;
import com.luckymiao.common.dto.T01_common_interface;

public interface T01_common_interfaceDAO extends DAO{

	public T01_common_interface getT01_common_intefaceByCiid(String ciid);

}
