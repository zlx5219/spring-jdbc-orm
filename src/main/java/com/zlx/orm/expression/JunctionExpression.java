package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.List;



/**
 * 集合表达式
 * @author zlx
 *
 */
public class JunctionExpression implements Expression
{
	private List<Expression> lstExp;
	private String op;
	private List<Object> lstParam;
	
	public JunctionExpression(String op)
	{
		this.op = op;
		lstExp = new ArrayList<Expression>();
		lstParam = new ArrayList<Object>();
	}

	public JunctionExpression add(Expression exp)
	{
		lstExp.add(exp);
		return this;
	}

	public String toSql()
	{
		if (lstExp.isEmpty())
			return "";
		if (lstExp.size() == 1)
		{
			this.lstParam.addAll(lstExp.get(0).getParam());
			return lstExp.get(0).toSql();
		}
		StringBuffer sql = new StringBuffer();

		for(int n = 0; n < lstExp.size() - 1; n++)
		{
			sql.append(lstExp.get(n).toSql()).append(' ').append(this.op).append(' ');
			this.lstParam.addAll(lstExp.get(n).getParam());
		}
		this.lstParam.addAll(lstExp.get(lstExp.size() - 1).getParam());
		sql.append(lstExp.get(lstExp.size() - 1).toSql()).append(' ');
		return sql.toString();
	}

	public List<Object> getParam()
	{
		return this.lstParam;
	}
}
