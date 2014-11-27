package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础的逻辑表达式
 * 运算符  !=, <>, =, >, <, >=, <=, like
 * @author zlx
 */
public class SimpleExpression implements Expression
{
	private String propertyName;
	//private Object value;
	private String option;// !=, <>, =, >, <, >=, <=, like
	private StringBuffer criteria;
	private List<Object> lstParam;
	
	public SimpleExpression(String propertyName, Object value, String option)
	{
		this.propertyName = propertyName;
		//this.value = value;
		this.option = option;
		criteria = new StringBuffer();
		lstParam = new ArrayList<Object>();
		lstParam.add(value);
	}

	public String toSql()
	{
		criteria.append(" ").append(this.propertyName).append(this.option).append(' ');
		criteria.append('?');
		criteria.append(" ");
		return criteria.toString();
	}

	public List<Object> getParam()
	{
		return lstParam;
	}
}
