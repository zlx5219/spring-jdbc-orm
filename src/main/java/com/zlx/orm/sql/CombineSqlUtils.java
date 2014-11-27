package com.zlx.orm.sql;

import java.io.Serializable;

import com.zlx.orm.sql.SelectQuery.Order;

/**
 * CombineSql工具类，用于创建CombineSql的实现。
 * @author zlx
 */
public class CombineSqlUtils
{
	public static <T> CombineSql createUpdate(T obj)
	{
		return new UpdateCombineSql<T>(obj);
	}

	public static <T> CombineSql createInster(T obj)
	{
		return new InsertCombineSql<T>(obj);
	}

	public static <T> CombineSql createDelete(Class<T> objClass, Serializable id)
	{
		return new DeleteCombineSql<T>(objClass, id);
	}

	public static <T> CombineSql createLoad(Class<T> objClass, Serializable id)
	{
		return new LoadCombineSql<T>(objClass, id);
	}

	public static <T> CombineSql createSelect(T obj, boolean isCount)
	{
		return new SelectCombineSql<T>(obj, isCount);
	}
	
	public static <T> CombineSql createSelect(T obj, String...fields)
	{
		return new SelectCombineSql<T>(obj, fields);
	}

	public static <T> CombineSql createSelectPage(T obj, int firstResult, int maxResults, Order... order) throws Exception
	{
		return new SelectPageCombineSql<T>(obj, firstResult, maxResults, order);
	}
}
