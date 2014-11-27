package com.zlx.orm.dao;

import java.io.Serializable;
import java.util.List;

import com.zlx.orm.PageInfo;
import com.zlx.orm.sql.SelectQuery.Order;

public interface BaseDao<T>
{
	public int add(T obj) throws Exception;

	public int update(T obj) throws Exception;

	public int delete(Class<T> objClass, Serializable id) throws Exception;

	public T load(Class<T> objClass, Serializable id) throws Exception;

	public List<T> search(T obj) throws Exception;

	public List<T> search(T obj, int currentPage, int numPerPage, Order... orders) throws Exception;

	public PageInfo searchPage(T obj, int currentPage, int numPerPage) throws Exception;
}
