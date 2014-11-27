package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * in
 * @author zlx
 *
 */
public class InExpression implements Expression
{
	private String propertyName;
	private Object[] values;
	private StringBuffer criteria;
	private List<Object> lstParam;

	public InExpression(String propertyName, Object[] values)
	{
		this.propertyName = propertyName;
		this.values = values;
		criteria = new StringBuffer();
		lstParam = new ArrayList<Object>();
		lstParam.addAll(Arrays.asList(values));
	}

	public String toSql()
	{
		criteria.append(' ').append(this.propertyName).append(' ').append("in ");
		if (values.length == 1)
			return criteria.append('(').append(values[0]).append(')').toString();
		criteria.append('(');
		for (int n = 0; n < values.length - 1; n++)
			criteria.append('?').append(',');
		criteria.append('?').append(')');
		return criteria.toString();
	}

	public List<Object> getParam()
	{
		return lstParam;
	}
}
