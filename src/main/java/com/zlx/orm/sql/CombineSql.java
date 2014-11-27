package com.zlx.orm.sql;

import java.util.List;

/**
 * 组装sql的基础接口。
 * @author zlx
 */
public interface CombineSql
{
	/**
	 * 获取查询语句。
	 * @return
	 * @throws Exception
	 */
	public String getSql() throws Exception;

	/**
	 * 获取参数集合。
	 * @return
	 */
	public List<Object> getParam();

	/**
	 * 添加已封装好的sql。
	 * @param query
	 * @throws Exception
	 */
	public void setSql(CombineSql query) throws Exception;

	public String toString();
}
