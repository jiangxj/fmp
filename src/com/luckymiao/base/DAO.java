package com.luckymiao.base;

import java.util.List;

public interface DAO {
	public List queryList(String sql);
	public List queryList(String sql, int intPage, int pageSize);
	public int execUpdate(String sql);
	public int execInsert(String sql);
	public int execDelete(String sql);
}
