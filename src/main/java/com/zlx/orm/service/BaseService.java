package com.zlx.orm.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.zlx.orm.PageInfo;
import com.zlx.orm.ServiceException;
import com.zlx.orm.sql.SelectQuery.Order;

public interface BaseService<T>
{
	public int add(T obj) throws ServiceException;

	/**
	 * 批量添加。
	 * @param lstObj
	 * @return
	 * @throws ServiceException
	 */
	public int batchAdd(List<T> lstObj) throws ServiceException;

	/**
	 * 添加数据
	 * 数据库中表的主键由数据库生成，返回数据库自增的主键
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public int addReturnKey(T obj) throws ServiceException;

	public int update(T obj) throws ServiceException;

	public int delete(Class<T> objClass, Serializable id) throws ServiceException;

	public T load(Class<T> objClass, Serializable id) throws ServiceException;

	public List<T> search(T obj) throws ServiceException;

	public List<T> searchByIds(Collection<Serializable> ids, Class<T> objClass) throws ServiceException;

	public T searchOne(T obj) throws ServiceException;

	public List<T> search(T obj, int currentPage, int numPerPage) throws ServiceException;

	public List<T> search(T obj, int currentPage, int numPerPage, Order... orders) throws ServiceException;

	public PageInfo searchPage(T obj, int currentPage, int numPerPage) throws ServiceException;

	public int delete(Serializable id) throws ServiceException;

	public T load(Serializable id) throws ServiceException;

	public List<T> searchByIds(Collection<Serializable> ids) throws ServiceException;
}
