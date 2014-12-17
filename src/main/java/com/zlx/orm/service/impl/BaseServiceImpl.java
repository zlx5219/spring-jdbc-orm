package com.zlx.orm.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zlx.orm.PageInfo;
import com.zlx.orm.ServiceException;
import com.zlx.orm.dao.BaseDao;
import com.zlx.orm.service.BaseService;
import com.zlx.orm.sql.SelectQuery.Order;

public abstract class BaseServiceImpl<T> implements BaseService<T>
{
	private BaseDao<T> baseDao;

	public BaseServiceImpl(BaseDao<T> baseDao)
	{
		this.baseDao = baseDao;
	}

	public int add(T obj) throws ServiceException
	{
		try
		{
			return baseDao.add(obj);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public int batchAdd(List<T> lstObj) throws ServiceException
	{
		int result = 1;
		int[] batchResult;
		try
		{
			batchResult = baseDao.batchAdd(lstObj);
			//logger.info("batch add result:" + Arrays.toString(batchResult));

			for (int n : batchResult)
			{
				if (n <= 0)
				{
					result = n;
					break;
				}
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
		return result;
	}
	
	public int addReturnKey(T obj) throws ServiceException
	{
		try
		{
			return baseDao.addReturnKey(obj);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public int update(T obj) throws ServiceException
	{
		try
		{
			return baseDao.update(obj);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public int delete(Class<T> objClass, Serializable id) throws ServiceException
	{
		try
		{
			return baseDao.delete(objClass, id);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public T load(Class<T> objClass, Serializable id) throws ServiceException
	{
		try
		{
			return baseDao.load(objClass, id);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public List<T> search(T obj) throws ServiceException
	{
		try
		{
			return baseDao.search(obj);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public T searchOne(T obj) throws ServiceException
	{
		try
		{
			return baseDao.searchOne(obj);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public List<T> search(T obj, int currentPage, int numPerPage, Order... orders) throws ServiceException
	{
		try
		{
			return baseDao.search(obj, currentPage, numPerPage, orders);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}

	public List<T> search(T obj, int currentPage, int numPerPage) throws ServiceException
	{
		Order[] orders = null;
		return this.search(obj, currentPage, numPerPage, orders);
	}

	public PageInfo searchPage(T obj, int currentPage, int numPerPage) throws ServiceException
	{
		try
		{
			return baseDao.searchPage(obj, currentPage, numPerPage);
		}
		catch (Exception e)
		{
			throw new ServiceException(e);
		}
	}
}
