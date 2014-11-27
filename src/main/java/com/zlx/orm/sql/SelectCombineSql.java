package com.zlx.orm.sql;

import java.util.List;

public class SelectCombineSql<T> extends BaseCombineSql<T> implements CombineSql
{
	private boolean isCount = false;
	private String[] showColumns = null;

	public SelectCombineSql(T obj)
	{
		super(obj.getClass());
		super.obj = obj;
	}

	public SelectCombineSql(T obj, boolean isCount)
	{
		this(obj);
		this.isCount = isCount;
	}

	public SelectCombineSql(T obj, String...showColumns)
	{
		this(obj);
		this.showColumns = showColumns;
	}

	public String getSql() throws Exception
	{
		sql = new StringBuffer();
		sql.append(" select ").append(this.getShowColumns()).append(" from ")
				.append(this.getTableName()).append(this.getSelectSql());
		return sql.toString();
	}

	private String getShowColumns()
	{
		if (this.isCount)
			return " count(*) ";
		if (this.showColumns == null || this.showColumns.length == 0)
			return " * ";
		StringBuffer strColumns = new StringBuffer();

		for (int n = 0; n < this.showColumns.length - 1; n++)
			strColumns.append(this.showColumns[n]).append(',');
		strColumns.append(this.showColumns[this.showColumns.length - 1]).append(' ');

		return strColumns.toString();
	}

	public List<Object> getParam()
	{
		return super.getSelectParam();
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
