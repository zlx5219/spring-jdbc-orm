package com.zlx.orm.expression;

import java.util.List;

/**
 * sql表达式接口。
 * 用于拼装sql的
 * @author zlx
 *
 */
public interface Expression
{
	public String toSql();
	
	public List<Object> getParam();
}
