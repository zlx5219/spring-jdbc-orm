package com.zlx.orm.service;

import java.io.Serializable;
import java.util.List;

import com.zlx.orm.PageInfo;
import com.zlx.orm.ServiceException;
import com.zlx.orm.sql.SelectQuery.Order;

public interface BaseService<T>
{
	public int add(T obj) throws ServiceException;

	public int update(T obj) throws ServiceException;

	public int delete(Class<T> objClass, Serializable id) throws ServiceException;

	public T load(Class<T> objClass, Serializable id) throws ServiceException;

	public List<T> search(T obj) throws ServiceException;

	public List<T> search(T obj, int currentPage, int numPerPage) throws ServiceException;

	public List<T> search(T obj, int currentPage, int numPerPage, Order... orders) throws ServiceException;

	public PageInfo searchPage(T obj, int currentPage, int numPerPage) throws ServiceException;
}
