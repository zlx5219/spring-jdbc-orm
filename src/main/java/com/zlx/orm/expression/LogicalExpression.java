package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.List;



/**
 * sql逻辑表达式
 * @author zlx
 *
 */
public class LogicalExpression implements Expression
{
	private Expression lexp;
	private Expression rexp;
	private String option;// and , or
	private StringBuffer criteria;
	private List<Object> lstParam;

	public LogicalExpression(Expression lexp, Expression rexp, String option)
	{
		this.lexp = lexp;
		this.rexp = rexp;
		this.option = option;
		criteria = new StringBuffer();
		lstParam = new ArrayList<Object>();
		lstParam.addAll(lexp.getParam());
		lstParam.addAll(rexp.getParam());
	}

	public String toSql()
	{
		return criteria.append(' ').append('(').append(lexp.toSql())
				.append(' ').append(option).append(' ')
				.append(rexp.toSql()).append(')').append(' ').toString();
	}

	public List<Object> getParam()
	{
		return lstParam;
	}
}
