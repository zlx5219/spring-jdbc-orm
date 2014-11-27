package com.zlx.orm.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.zlx.orm.expression.AndJunctionExpression;
import com.zlx.orm.expression.BetweenExpression;
import com.zlx.orm.expression.Expression;
import com.zlx.orm.expression.InExpression;
import com.zlx.orm.expression.IsNullExpression;
import com.zlx.orm.expression.LogicalExpression;
import com.zlx.orm.expression.OrJunctionExpression;
import com.zlx.orm.expression.PropertyExpression;
import com.zlx.orm.expression.SimpleExpression;

/**
 * 数据库表达式工具类。
 * @author zlx
 */
public class ExpressionUtil
{
	/**
	 * 等于值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression eq(String propertyName, Object value)
	{
		return new SimpleExpression(propertyName, value, "=");
	}

	/**
	 * 不等于值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression ne(String propertyName, Object value)
	{
		return new SimpleExpression(propertyName, value, "<>");
	}

	/**
	 * like值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression like(String propertyName, Object value) 
	{
		return new SimpleExpression(propertyName, value, " like ");
	}

	/**
	 * 大于值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression gt(String propertyName, Object value) 
	{
		return new SimpleExpression(propertyName, value, ">");
	}

	/**
	 * 小于值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression lt(String propertyName, Object value)
	{
		return new SimpleExpression(propertyName, value, "<");
	}

	/**
	 * 小于等于值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression le(String propertyName, Object value)
	{
		return new SimpleExpression(propertyName, value, "<=");
	}

	/**
	 * 大于等于值
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression ge(String propertyName, Object value)
	{
		return new SimpleExpression(propertyName, value, ">=");
	}

	/**
	 * between and
	 * @param propertyName
	 * @param lo
	 * @param hi
	 * @return
	 */
	public static Expression between(String propertyName, Object lo, Object hi)
	{
		return new BetweenExpression(propertyName, lo, hi);
	}

	/**
	 * in
	 * @param propertyName
	 * @param values
	 * @return
	 */
	public static Expression in(String propertyName, Object[] values)
	{
		return new InExpression(propertyName, values);
	}

	/**
	 * in
	 * 
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	public static Expression in(String propertyName, Collection<Object> values)
	{
		return new InExpression(propertyName, values.toArray());
	}

	/**
	 * is null
	 * @return Expression
	 */
	public static Expression isNull(String propertyName)
	{
		return new IsNullExpression(propertyName, true);
	}

	/**
	 * 等于字段
	 * @return Expression
	 */
	public static Expression eqProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "=");
	}

	/**
	 * 不等于字段
	 * @return Expression
	 */
	public static Expression neProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "<>");
	}

	/**
	 * 小于字段
	 * @return Expression
	 */
	public static Expression ltProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "<");
	}

	/**
	 * 小于等于字段
	 * @return Expression
	 */
	public static Expression leProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "<=");
	}

	/**
	 * 大于字段
	 * @return Expression
	 */
	public static Expression gtProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, ">");
	}

	/**
	 * 大于等于
	 * @return Expression
	 */
	public static Expression geProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, ">=");
	}

	/**
	 * is not null
	 * 
	 * @return Expression
	 */
	public static Expression isNotNull(String propertyName)
	{
		return new IsNullExpression(propertyName, false);
	}

	/**
	 * and
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	public static Expression and(Expression lhs, Expression rhs) 
	{
		return new LogicalExpression(lhs, rhs, "and");
	}

	/**
	 * 或
	 * @param lhs
	 * @param rhs
	 * @return Expression
	 */
	public static Expression or(Expression lhs, Expression rhs) 
	{
		return new LogicalExpression(lhs, rhs, "or");
	}
	
	/**
	 * 添加多个等于并列条件。
	 * @param propertyNameValues
	 * @return
	 */
	public static Expression allEqAnd(Map<String, Object> propertyNameValues)
	{
		AndJunctionExpression andJun = new AndJunctionExpression();
		Iterator<Entry<String, Object>> iter = propertyNameValues.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry<String, Object> me = iter.next();
			andJun.add(ExpressionUtil.eq(me.getKey(), me.getValue()));
		}
		return andJun;
	}

	/**
	 * 添加多个等于的OR条件。
	 * @param propertyNameValues
	 * @return
	 */
	public static Expression allEqOr(Map<String, Object> propertyNameValues)
	{
		OrJunctionExpression andJun = new OrJunctionExpression();
		Iterator<Entry<String, Object>> iter = propertyNameValues.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry<String, Object> me = iter.next();
			andJun.add(ExpressionUtil.eq(me.getKey(), me.getValue()));
		}
		return andJun;
	}
}
