package com.luckymiao.base;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseDAO extends SqlSessionDaoSupport implements Serializable{
	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	public List queryList(String sql){
		return this.getSqlSession().selectList("querySQL", sql);
	}
	
	public List queryList(String sql, int intPage, int pageSize){
		return this.getSqlSession().selectList("querySQL", sql, new RowBounds(intPage, pageSize));
	}
	
	public int execUpdate(String sql){
		return this.getSqlSession().update("updateSQL", sql);
	}
	
	public int execInsert(String sql){
		return this.getSqlSession().update("insertSQL", sql);
	}
	
	public int execDelete(String sql){
		return this.getSqlSession().update("deleteSQL", sql);
	}
	
	
}
