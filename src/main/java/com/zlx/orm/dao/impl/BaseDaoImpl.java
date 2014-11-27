package com.zlx.orm.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.PageInfo;
import com.zlx.orm.dao.BaseDao;
import com.zlx.orm.sql.CombineSql;
import com.zlx.orm.sql.CombineSqlUtils;
import com.zlx.orm.sql.SelectQuery.Order;

/**
 * 基础Dao实现
 * @author zlx
 *
 * @param <T>
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T>
{
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public int add(T obj) throws Exception
	{
		CombineSql comSql = CombineSqlUtils.createInster(obj);
		return this.updateSqlByCombineSql(comSql);
	}

	public int update(T obj) throws Exception
	{
		CombineSql comSql = CombineSqlUtils.createUpdate(obj);
		return this.updateSqlByCombineSql(comSql);
	}

	public int delete(Class<T> objClass, Serializable id) throws Exception
	{
		CombineSql comSql = CombineSqlUtils.createDelete(objClass, id);
		return this.updateSqlByCombineSql(comSql);
	}

	public T load(Class<T> objClass, Serializable id) throws Exception
	{
		CombineSql comSql = CombineSqlUtils.createLoad(objClass, id);
		return BaseEntity.mapToObj(this.loadSqlByCombineSql(comSql), objClass);
	}

	@SuppressWarnings("unchecked")
	public List<T> search(T obj) throws Exception
	{
		CombineSql comSql = CombineSqlUtils.createSelect(obj, false);
		return (List<T>) BaseEntity.mapToObjs(this.querySqlByCombineSql(comSql), obj.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<T> search(T obj, int currentPage, int numPerPage, Order... orders) throws Exception
	{
		int startIndex = (currentPage - 1) * numPerPage;
		CombineSql comSql = CombineSqlUtils.createSelectPage(obj, startIndex, numPerPage, orders);
		return (List<T>) BaseEntity.mapToObjs(this.querySqlByCombineSql(comSql), obj.getClass());
	}

	public PageInfo searchPage(T obj, int currentPage, int numPerPage) throws Exception
	{
		CombineSql comSql = CombineSqlUtils.createSelect(obj, true);
		int count = 0;
		if (comSql.getParam() == null || comSql.getParam().isEmpty())
			count = jdbcTemplate.queryForInt(comSql.getSql());
		else
			count = jdbcTemplate.queryForInt(comSql.getSql(), comSql.getParam().toArray(new Object[0]));
		return new PageInfo(count, currentPage, numPerPage);
	}

	/**
	 * 更新操作。
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	protected int updateSqlByCombineSql(CombineSql sql) throws Exception
	{
		try
		{
			if (sql.getParam() == null || sql.getParam().isEmpty())
				return jdbcTemplate.update(sql.getSql());
			return jdbcTemplate.update(sql.getSql(), sql.getParam().toArray(new Object[0]));
		}
		catch (EmptyResultDataAccessException e)
		{
			return 0;
		}
	}

	/**
	 * load数据操作。
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	protected Map<String, Object> loadSqlByCombineSql(CombineSql sql) throws Exception
	{
		try
		{
			if (sql.getParam() == null || sql.getParam().isEmpty())
				return jdbcTemplate.queryForMap(sql.getSql());
			return jdbcTemplate.queryForMap(sql.getSql(), sql.getParam().toArray(new Object[0]));
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
	}

	/**
	 * 查询操作。
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	protected List<Map<String, Object>> querySqlByCombineSql(CombineSql sql) throws Exception
	{
		try
		{
			if (sql.getParam() == null || sql.getParam().isEmpty())
				return jdbcTemplate.queryForList(sql.getSql());
			return jdbcTemplate.queryForList(sql.getSql(), sql.getParam().toArray(new Object[0]));
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
	}
}