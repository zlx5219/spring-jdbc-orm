package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性为空逻辑判断
 * @author zlx
 *
 */
public class IsNullExpression implements Expression
{
	private String propertyName;
	private boolean isNull;
	private StringBuffer criteria;
	private List<Object> lstParam;

	public IsNullExpression(String propertyName, boolean isNull)
	{
		this.propertyName = propertyName;
		this.isNull = isNull;
		criteria = new StringBuffer();
		lstParam = new ArrayList<Object>();
	}

	public String toSql()
	{
		criteria.append(" ").append(this.propertyName);
		if (isNull) criteria.append(" is null ");
		else criteria.append(" is not null ");
		return criteria.toString();
	}

	public List<Object> getParam()
	{
		return lstParam;
	}
	
}
