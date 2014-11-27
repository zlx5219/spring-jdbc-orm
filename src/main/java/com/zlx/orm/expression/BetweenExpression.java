package com.zlx.orm.expression;

import java.util.ArrayList;
import java.util.List;



/**
 * between and 
 * @author zlx
 *
 */
public class BetweenExpression implements Expression
{
	private String propertyName;
//	private Object lValue;
//	private Object rValue;
	private StringBuffer criteria;
	private List<Object> lstParam;

	public BetweenExpression(String propertyName, Object lValue, Object rValue)
	{
		this.propertyName = propertyName;
//		this.lValue = lValue;
//		this.rValue = rValue;
		criteria = new StringBuffer();
		lstParam = new ArrayList<Object>();
		lstParam.add(lValue);
		lstParam.add(rValue);
	}

	public String toSql()
	{
		criteria.append(' ').append(propertyName).append(" between ").append('?').append(" and ").append('?').append(' ');
		return criteria.toString();
	}

	public List<Object> getParam()
	{
		return lstParam;
	}
}
