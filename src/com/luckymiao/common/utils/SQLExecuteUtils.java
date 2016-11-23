package com.luckymiao.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.luckymiao.quartz.QuartzJob;

public class SQLExecuteUtils {
	Logger logger = Logger.getLogger(QuartzJob.class);
	public static final String dbUseCode = "UTF-8";
	public static List queryForList(Connection conn, String sql) throws Exception{
		return queryForList(conn, sql, null);
	}
	public static List queryForList(Connection conn, String sql, String className) throws Exception{
		return queryForList(conn, sql, className, null);
	}
	public static List queryForList(Connection conn, String sql, String className, List condList) throws Exception{
		List resultList = new ArrayList();
		if(conn == null || "".equals(sql.trim())){
			return resultList;
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conn.prepareStatement(sql);
			setCondForStatement(stmt, condList);
			rs = stmt.executeQuery();
			resultList = getListFromResultSet(rs, className);
		}catch (SQLException e) {
			
			throw e;
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	public static int update(Connection conn, String sql, List condList) throws SQLException {
		if (sql == null || conn == null){
			return 0;
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int j = 0; j < condList.size(); j++) {
				pstmt.setObject(j + 1, condList.get(j));
			}
			int i = pstmt.executeUpdate();
			return i;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static int update(Connection conn, String sql) throws SQLException {
		if (sql == null || conn == null){
			return 0;
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			return i;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void updateBatch(Connection conn, List<String> sqls) throws SQLException {
		if (sqls == null || conn == null){
			return ;
		}
		conn.setAutoCommit(false);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int i = 1;
			for (String sql : sqls) {
				if(i%100 == 0){
					stmt.executeBatch();
				}
				stmt.addBatch(sql);
				
			}
			stmt.executeBatch();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				if (stmt != null){
					stmt.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private static void setCondForStatement(PreparedStatement stmt, List condList) throws SQLException{
		if ((condList != null) && (condList.size() > 0)) {
			for (int j = 0; j < condList.size(); j++) {
				if ((condList.get(j) instanceof String))
					stmt.setObject(j + 1, condList.get(j));
				else {
					stmt.setObject(j + 1, condList.get(j));
				}
			}
		}
	}
	
	private static List getListFromResultSet(ResultSet rs, String className) throws Exception{
		List resultList = new ArrayList();
		if(rs == null){
			return resultList;
		}
		Object obj = null;
		boolean objectFlag = false;
		if(className != null && !"".equals(className)){
			objectFlag = true;
		}
		while(rs.next()){
			if(objectFlag){
				obj = Class.forName(className).newInstance();
			}else{
				obj = new TreeMap();
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int colsize = rsmd.getColumnCount();
			for(int i=1; i<= colsize; i++){
				String colName = rsmd.getColumnName(i).toUpperCase();
				int colType = rsmd.getColumnType(i);
				String colValue = "";
				if(colType == 91 || colType == 92 || colType == 93){//date
					Timestamp temp = rs.getTimestamp(i);
					long dateValue = 0L;
					if(temp != null){
						dateValue = temp.getTime();
					}
					Date date = new Date(dateValue);
					colValue = DateUtils.dateToString(date);
				}else{
					colValue = rs.getString(i);
					if(colValue == null || "null".equalsIgnoreCase(colValue.trim()) || "".equals(colValue.trim())){
						colValue = "";
					}
				}
				if(objectFlag){
					ReflectUtils.invokeSetMethod(obj, colName.toLowerCase(), colValue);
				}else{
					((TreeMap)obj).put(colName, colValue);
				}
			}
			resultList.add(obj);
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		System.out.println(new Object());
	}
	
}
