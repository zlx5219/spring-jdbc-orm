package com.zlx.orm;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.zlx.orm.annotation.TableColumn;

/**
 * 基础实体。
 * @author zlx
 *
 */
public abstract class BaseEntity implements Serializable
{
	private static final long serialVersionUID = -696673736536660920L;

	public abstract String toString();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);
	
	/**
	 * map转对象。
	 * @param map
	 * @param objClass
	 * @return
	 */
	public static <T> T mapToObj(Map<String, Object> map, Class<T> objClass)
	{
		if (map == null || objClass == null)
			return null;
		
		Field[] fields = getFieldByClass(objClass);
		T obj = null;

		try
		{
			obj = objClass.newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IllegalAccessException e1)
		{
			e1.printStackTrace();
			return null;
		}
		setFieldValue(map, obj, fields, true);
		return obj;
	}

	public static <T> List<T> mapToObjs(List<Map<String, Object>> lstMap, Class<T> objClass)
	{
		if (lstMap == null || lstMap.isEmpty())
			return null;
		List<T> lst = new ArrayList<T>();
		for (Map<String, Object> map : lstMap)
		{
			lst.add(BaseEntity.mapToObj(map, objClass));
		}
		return lst;
	}

	/**
	 * 调用set方法。
	 * @param obj
	 * @param att
	 * @param value
	 * @param type
	 */
	private static void setter(Object obj, String att, Object value, Class<?> type)
	{
		att = StringUtil.toUpperCaseFirstOne(att);
		try
		{
			Method method = obj.getClass().getMethod("set" + att, type);
			method.invoke(obj, value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 调用get方法
	 * @param obj
	 * @param att
	 * @return
	 */
	public static Object getter(Object obj, String att)
	{
		att = StringUtil.toUpperCaseFirstOne(att);
		try
		{
			Method method = obj.getClass().getMethod("get" + att);
			return method.invoke(obj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 初始化方法，用户子类带参数得构造方法
	 * @param map
	 * @param objClass
	 * @param obj
	 * @return
	 */
	public <T> T init(Map<String, Object> map, T obj)
	{
		if (map == null || obj == null)
			return null;

		Field[] fields = getFieldByClass(obj.getClass());

		setFieldValue(map, obj, fields, false);
		return obj;
	}

	/**
	 * 设置字段值。
	 * @param map
	 * @param obj
	 * @param fields
	 * @param isMethod
	 */
	private static <T> void setFieldValue(Map<String, Object> map, T obj, Field[] fields, boolean isMethod)
	{
		TableColumn column = null;
		Object value = null;
		String key = null;

		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (column == null)
			{
				continue;
			}
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();
			value = map.get(key);

			if (value == null)
				continue;
			
			if (isMethod)
			{
				setter(obj, f.getName(), value, f.getType());
				continue;
			}
			f.setAccessible(true);
			try
			{
				f.set(obj, value);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对象转map。
	 * @param obj
	 * @param objClass
	 * @return
	 */
	public <T> Map<String, Object> objToMap(T obj)
	{
		Field[] fields = getFieldByClass(obj.getClass());
		TableColumn column = null;
		Object value = null;
		String key = null;
		Map<String, Object> map = new HashMap<String, Object>();

		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (column == null)
				continue;
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();
			
			f.setAccessible(true);
			try
			{
				value = f.get(obj);
				if (value == null)
					continue;

				map.put(key, value);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 获取实体主键或数据库字段主键名称。
	 * @param objClass
	 * @param isDataColumn true 返回数据库对应列名  false 返回属性列名。
	 * @return
	 * @throws Exception
	 */
	public static <T> String getKeyByClass(Class<T> objClass, boolean isDataColumn) throws Exception
	{
		Field[] lstField = getFieldByClass(objClass);
		TableColumn column = null;
		String objColumn = null;
		String dataColumn = null;
		for (Field f : lstField)
		{
			column = f.getAnnotation(TableColumn.class);
			if (column == null)
				continue;
			if (column.isKey())
			{
				objColumn = f.getName();
				dataColumn = column.value();
				if (dataColumn == null || "".equals(dataColumn))
					dataColumn = objColumn;
				break;
			}
		}
		if (objColumn == null || "".equals(objColumn))
			throw new Exception("Entity primary key not found");
		return isDataColumn ?  dataColumn : objColumn;
	}

	/**
	 * 获取主键的值。
	 * @param lst
	 * @param objClass
	 * @return
	 * @throws Exception
	 */
	public static <T> Collection<Serializable> getKeysValues(List<T> lst, Class<T> objClass) throws Exception
	{
		Collection<Serializable> lstResult = new LinkedHashSet<Serializable>();
		String objColumn = getKeyByClass(objClass, false);

		for (T t : lst)
		{
			lstResult.add((Serializable)getter(t, objColumn));
		}
		return lstResult;
	}

	/**
	 * 获取所有成员变量（包括继承的私有成员变量）
	 * @param objClass
	 * @return
	 */
	public static Field[] getFieldByClass(Class<?> objClass)
	{
		List<Field> lstField = new ArrayList<Field>();
		Field[] tempField = null;
		if (objClass == null || objClass.getDeclaredFields() == null || objClass.equals(Object.class))
			return null;
		if (objClass.getSuperclass() != null)
		{
			tempField = getFieldByClass(objClass.getSuperclass());
			if (tempField != null)
			{
				for (Field f : tempField)
				{
					if (!f.getName().equals("serialVersionUID"))
						lstField.add(f);
				}
			}
		}
		tempField = objClass.getDeclaredFields();
		if (tempField != null)
		{
			for (Field f : tempField)
			{
				if (!f.getName().equals("serialVersionUID"))
					lstField.add(f);
			}
		}
		return lstField.toArray(new Field[0]);
	}
}
