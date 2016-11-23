package com.luckymiao.common.mybatis.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.luckymiao.common.utils.LogUtils;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 老规矩，签名里要拦截的类型只能是接口。
 * 
 * @author 湖畔微风
 * 
 */
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	private static final Logger logger = LogUtils
			.getLogger(PageInterceptor.class.getName());
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private String dialect = "";
	private static final Map<String, PageParameter> pageInfoMap = new HashMap();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		MetaObject metaRoutingStatementHandler = MetaObject.forObject(
				routingStatementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);
		Configuration configuration = (Configuration) metaRoutingStatementHandler
				.getValue("delegate.configuration");
		RowBounds rowBounds = (RowBounds) metaRoutingStatementHandler
				.getValue("delegate.rowBounds");
		MappedStatement mappedStatement = (MappedStatement) metaRoutingStatementHandler
				.getValue("delegate.mappedStatement");
		Connection connection = (Connection) invocation.getArgs()[0];
		if (rowBounds.NO_ROW_LIMIT == rowBounds.getLimit()
				&& rowBounds.NO_ROW_OFFSET == rowBounds.getOffset()) {// 不分页

		} else {
			BoundSql boundSql = routingStatementHandler.getBoundSql();
			String sql = boundSql.getSql();
			dialect = configuration.getDatabaseId();
			PageParameter pageParameter = new PageParameter(rowBounds
					.getOffset(), rowBounds.getLimit());
			setPageParameter(sql, connection, mappedStatement, boundSql,
					pageParameter);
			String sqlPage = buildPageSql(sql, pageParameter);
			MetaObject.forObject(boundSql, DEFAULT_OBJECT_FACTORY,
					DEFAULT_OBJECT_WRAPPER_FACTORY).setValue("sql", sqlPage);
			// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
			metaRoutingStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaRoutingStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
            
			String oldSqlId = mappedStatement.getId();
			String sqls = boundSql.getSql();
			pageInfoMap.put(oldSqlId.substring(oldSqlId.indexOf(".")+1), pageParameter);
		}
		// 将执行权交给下一个拦截器6
		return invocation.proceed();
	}

	/**
	 * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用者就可用通过 分页参数
	 * <code>PageParameter</code>获得相关信息。
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	private void setPageParameter(String sql, Connection connection,
			MappedStatement mappedStatement, BoundSql boundSql,
			PageParameter page) {
		// 记录总记录数
		String countSql = "select count(0) from (" + sql + ") as total";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),
					countSql, boundSql.getParameterMappings(), boundSql
							.getParameterObject());
			setParameters(countStmt, mappedStatement, countBS, boundSql
					.getParameterObject());
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			page.setTotalCount(totalCount);
			int totalPage = totalCount / page.getPageSize()
					+ ((totalCount % page.getPageSize() == 0) ? 0 : 1);
			page.setTotalPage(totalPage);

		} catch (SQLException e) {
			logger.error("Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
		}

	}

	/**
	 * 对SQL参数(?)设值
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(
				mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	/**
	 * 根据数据库类型，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String buildPageSql(String sql, PageParameter page) {
		if (page != null) {
			StringBuilder pageSql = new StringBuilder();
			if ("mysql".equalsIgnoreCase(dialect)) {
				pageSql = buildPageSqlForMysql(sql, page);
			} else if ("oracle".equalsIgnoreCase(dialect)) {
				pageSql = buildPageSqlForOracle(sql, page);
			} else {
				return sql;
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	/**
	 * mysql的分页语句
	 * 
	 * @param sql
	 * @param page
	 * @return String
	 */
	public StringBuilder buildPageSqlForMysql(String sql, PageParameter page) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((page.getIntPage() - 1)
				* page.getPageSize());
		pageSql.append(sql);
		pageSql.append(" limit " + beginrow + "," + page.getPageSize());
		return pageSql;
	}

	/**
	 * 参考hibernate的实现完成oracle的分页
	 * 
	 * @param sql
	 * @param page
	 * @return String
	 */
	public StringBuilder buildPageSqlForOracle(String sql, PageParameter page) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((page.getIntPage() - 1)
				* page.getPageSize());
		String endrow = String.valueOf(page.getIntPage()
				* page.getPageSize());

		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(sql);
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql;
	}

	@Override
	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}
	
	public PageParameter getPageParameter(String sqlId){
		return pageInfoMap.get(sqlId);
	}

}
