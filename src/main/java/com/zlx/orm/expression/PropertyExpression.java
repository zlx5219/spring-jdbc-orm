package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性之间的逻辑
 * 运算符 <>, =, >, <, >=, <=
 * @author zlx
 *
 */
public class PropertyExpression implements Expression
{
	private String propertyName;
	private String otherPropertyName;
	private String option;// <>, =, >, <, >=, <=
	private StringBuffer criteria;
	private List<Object> lstParam;

	public PropertyExpression(String propertyName, String otherPropertyName, String option)
	{
		this.propertyName = propertyName;
		this.otherPropertyName = otherPropertyName;
		this.option = option;
		criteria = new StringBuffer();
		lstParam = new ArrayList<Object>();
	}

	public String toSql()
	{
		criteria.append(" ").append(this.propertyName).append(this.option);
		criteria.append(this.otherPropertyName);
		criteria.append(" ");
		return criteria.toString();
	}

	public List<Object> getParam()
	{
		return lstParam;
	}
}
