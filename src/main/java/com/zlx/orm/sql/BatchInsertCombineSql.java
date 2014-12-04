package com.zlx.orm.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量插入sql实现
 * @author zlx
 *
 * @param <T>
 */
public class BatchInsertCombineSql<T> implements CombineSql
{
	private List<InsertCombineSql<T>> lstSql;
	private List<Object> lstParam;

	public BatchInsertCombineSql(List<T> lstObj)
	{
		lstSql = new ArrayList<InsertCombineSql<T>>();
		lstParam = new ArrayList<Object>();

		InsertCombineSql<T> temp;
		for (T o : lstObj)
		{
			temp = new InsertCombineSql<T>(o);
			lstSql.add(temp);
			lstParam.add(temp.getParam().toArray(new Object[0]));
		}
	}

	@Override
	public String getSql() throws Exception
	{
		return lstSql.get(0).getSql();
	}

	@Override
	public List<Object> getParam()
	{
		return lstParam;
	}

	@Override
	public void setSql(CombineSql query)
	{
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
