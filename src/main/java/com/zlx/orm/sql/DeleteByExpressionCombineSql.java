package com.zlx.orm.sql;

import java.util.ArrayList;
import java.util.List;

import com.zlx.orm.annotation.Table;
import com.zlx.orm.expression.Expression;

/**
 * 删除sql实现
 * @author zlx
 *
 * @param <T>
 */
public class DeleteByExpressionCombineSql implements CombineSql
{
	private String tableName;
	private List<Expression> lstExp;
	private Class<?> objClass;
	private List<Object> lstParam;
	private StringBuffer baseSql;
	public DeleteByExpressionCombineSql(Class<?> objClass)
	{
		this.objClass = objClass;
		lstExp = new ArrayList<Expression>();
	}

	@Override
	public String getSql() throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql = this.getBaseSql(sql);
		sql = this.getWhere(sql);

		return sql.toString();
	}

	private StringBuffer getBaseSql(StringBuffer sql) throws Exception
	{
		if (this.baseSql != null)
		{
			sql.append(baseSql);
			return sql;
		}
		sql.append("delete from ").append(this.getTableName()).append(" where 1 = 1 ");
		return sql;
	}

	private StringBuffer getWhere(StringBuffer sql)
	{
		if (!lstExp.isEmpty())
		{
			for (int n = 0; n < lstExp.size(); n++)
				sql.append(" and ").append(lstExp.get(n).toSql()).append(' ');
		}
		return sql;
	}

	@Override
	public List<Object> getParam()
	{
		if (lstParam != null)
			return lstParam;
		lstParam = new ArrayList<Object>();
		if (this.lstExp.isEmpty())
			return lstParam;
		for (Expression e : lstExp)
		{
			if (e.getParam() == null)
				continue;
			lstParam.addAll(e.getParam());
		}
		return lstParam;
	}

	@Override
	public void setSql(CombineSql query)
	{
		// TODO Auto-generated method stub
	}

	public DeleteByExpressionCombineSql add(Expression exp)
	{
		if (exp != null)
			lstExp.add(exp);
		return this;
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

	private String getTableName() throws Exception
	{
		if (tableName != null && !"".equals(tableName))
			return tableName;
		Table table = objClass.getAnnotation(Table.class);
		if (table == null || table.name().equals(""))
			throw new Exception("get table name error, annotation not exist");
		tableName = table.name();
		return tableName;
	}
}
