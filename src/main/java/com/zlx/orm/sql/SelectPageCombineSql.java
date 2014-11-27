package com.zlx.orm.sql;

import java.util.List;

import com.zlx.orm.sql.SelectQuery.Order;

/**
 * 查询分页sql实现。
 * @author zlx
 *
 * @param <T>
 */
public class SelectPageCombineSql<T> implements CombineSql
{
	private SelectQuery query;
	public SelectPageCombineSql(T obj, int firstResult, int maxResults, Order... order) throws Exception
	{
		query = SelectQuery.newInstance(obj.getClass());
		query.setSql(new SelectCombineSql<T>(obj));
		query.add(order).setFirstResult(firstResult).setMaxResults(maxResults);
	}

	public String getSql() throws Exception
	{
		return query.getSql();
	}

	public List<Object> getParam()
	{
		return query.getParam();
	}

	public void setSql(CombineSql query)
	{
		// TODO Auto-generated method stub
		
	}
	
	public String toString()
	{
		try
		{
			return this.getSql();
		}
		catch (Exception e)
		{
		}
		return "-----";
	}
}
