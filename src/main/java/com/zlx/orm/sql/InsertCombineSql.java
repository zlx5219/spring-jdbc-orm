package com.zlx.orm.sql;

import java.util.List;

/**
 * 插入sql实现
 * @author zlx
 *
 * @param <T>
 */
public class InsertCombineSql<T> extends BaseCombineSql<T> implements CombineSql
{
	public InsertCombineSql(T obj)
	{
		super(obj.getClass()); // 初始化
		super.obj = obj;
	}

	@Override
	public String getSql() throws Exception
	{
		sql = new StringBuffer();
		sql.append("insert into ").append(super.getTableName())
		.append(" (").append(this.getFields()).append(") ")
		.append("values(").append(this.getFieldsParamIndex()).append(")");
		return sql.toString();
	}

	@Override
	public List<Object> getParam()
	{
		return super.getInsertParam();
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
