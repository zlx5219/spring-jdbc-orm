package com.zlx.orm.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.PageInfo;
import com.zlx.orm.dao.BaseDao;
import com.zlx.orm.sql.CombineSql;
import com.zlx.orm.sql.CombineSqlUtils;
import com.zlx.orm.sql.SelectQuery;
import com.zlx.orm.sql.SelectQuery.Order;
import com.zlx.orm.util.ExpressionUtil;

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

	public int[] batchAdd(List<T> lstObj) throws DataAccessException, Exception
	{	
		CombineSql comSql = CombineSqlUtils.createBatchInster(lstObj);
		return this.batchUpdateSqlByCombineSql(comSql);
	}
	
	public int addReturnKey(T obj) throws DataAccessException, Exception
	{
		CombineSql comSql = CombineSqlUtils.createInster(obj);
		return this.insertByCombineSqlReturnKey(comSql);
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

	public List<T> searchByIds(Collection<Serializable> ids, Class<T> objClass) throws DataAccessException, Exception
	{
		SelectQuery query = SelectQuery.newInstance(objClass);
		query.add(ExpressionUtil.in(BaseEntity.getKeyByClass(objClass, true), ids.toArray(new Object[0])));

		return (List<T>) BaseEntity.mapToObjs(this.querySqlByCombineSql(query), objClass);
	}

	public T searchOne(T obj) throws DataAccessException, Exception
	{
		List<T> lst = this.search(obj);
		
		if (lst == null || lst.isEmpty())
			return null;
		if (lst.size() > 1)
			throw new Exception(" More than one returns the result ");
		return lst.get(0);
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
	 * 批量执行更新语句。
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	protected int[] batchUpdateSqlByCombineSql(CombineSql sql) throws DataAccessException, Exception
	{
		List<Object[]> lstParam = new ArrayList<Object[]>();
		for (Object param: sql.getParam())
		{
			if (param instanceof Object[])
				lstParam.add((Object[])param);
		}
		try
		{
			return jdbcTemplate.batchUpdate(sql.getSql(), lstParam);
		}
		catch (EmptyResultDataAccessException e)
		{
			return new int[]{0};
		}
	}
	
	/**
	 * 添加数据，返回数据库自增key
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	protected int insertByCombineSqlReturnKey(final CombineSql sql) throws DataAccessException, Exception
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();

		try
		{
			this.jdbcTemplate.update(new PreparedStatementCreator()
				{
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException
					{
						PreparedStatement ps;
						List<Object> param = sql.getParam();
						try
						{
							ps = con.prepareStatement(sql.getSql(), Statement.RETURN_GENERATED_KEYS);
							for (int n = 1; n <= param.size(); n++)
							{
								ps.setObject(n, param.get(n - 1));
							}
							return ps;
						}
						catch (Exception e)
						{
							//logger.error("add return key error:", e);
							throw new SQLException(e);
						}
						
					}
				}, keyHolder);
			return keyHolder.getKey().intValue();
		}
		catch(Exception e)
		{
			//logger.error("add return key error:", e);
			throw e;
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

	public int delete(Serializable id) throws DataAccessException, Exception
	{
		CombineSql comSql = CombineSqlUtils.createDelete(getObjClass(), id);
		return this.updateSqlByCombineSql(comSql);
	}

	public T load(Serializable id) throws DataAccessException, Exception
	{
		CombineSql comSql = CombineSqlUtils.createLoad(getObjClass(), id);
		return BaseEntity.mapToObj(this.loadSqlByCombineSql(comSql), getObjClass());
	}

	public List<T> searchByIds(Collection<Serializable> ids) throws DataAccessException, Exception
	{
		SelectQuery query = SelectQuery.newInstance(getObjClass());
		query.add(ExpressionUtil.in(BaseEntity.getKeyByClass(getObjClass(), true), ids.toArray(new Object[0])));

		return (List<T>) BaseEntity.mapToObjs(this.querySqlByCombineSql(query), getObjClass());
	}

	protected Class<T> entityClass = null;
	@SuppressWarnings("unchecked")
	protected Class<T> getObjClass()
	{
		if (entityClass != null)
			return entityClass;
		Type type = getClass().getGenericSuperclass();  
        Type[] trueType = ((ParameterizedType) type).getActualTypeArguments();
        this.entityClass = (Class<T>) trueType[0];

        return entityClass;
	}
}
