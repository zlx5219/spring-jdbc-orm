package com.zlx.orm.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.Constants;
import com.zlx.orm.StringUtil;
import com.zlx.orm.annotation.Table;
import com.zlx.orm.annotation.TableColumn;

/**
 * 拼装接口的基础实现。
 * @author zlx
 *
 * @param <T>
 */
public abstract class BaseCombineSql<T> implements CombineSql
{
	protected StringBuffer sql;
	protected Class<?> objClass;
	protected T obj;
	protected List<Object> lstParam;
	protected Field[] fields;
	private String tableName;

	public BaseCombineSql(Class<?> objClass)
	{
		this.objClass = objClass;
		//this.obj = obj;
		fields = getFieldByClass(objClass);
	}
	
	public Field[] getFieldByClass(Class<?> objClass)
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

	/**
	 * 获取字段列表(插入使用)
	 * @return
	 */
	public String getFields()
	{
		StringBuffer sqlField = new StringBuffer();
		TableColumn column = null;
		String key = null;

		int count = 0;
		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (!this.valdateTableColumnNullAndIncrement(column))
				continue;
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();

			if (count > 0)
				sqlField.append(",");
			sqlField.append(key);
			count++;
		}
		return sqlField.toString();
	}

	/**
	 * 获取字段参数位置。
	 * @return
	 */
	public String getFieldsParamIndex()
	{
		int count = this.getFieldsCount();
		StringBuffer sql = new StringBuffer();
		for (int n = 0; n < count - 1; n++)
			sql.append("?").append(',');
		sql.append("?");
		return sql.toString();
	}

	/**
	 * 获取字段数量。（插入使用）
	 * @return
	 */
	public int getFieldsCount()
	{
		int count = 0;

		for (Field f : fields)
		{
			if (!this.valdateTableColumnNullAndIncrement(f.getAnnotation(TableColumn.class)))
			{
				continue;
			}
			count++;
		}
		return count;
	}

	/**
	 * 获取表明
	 * @return
	 * @throws Exception
	 */
	public String getTableName() throws Exception
	{
		if (tableName != null && !"".equals(tableName))
			return tableName;
		Table table = (Table) objClass.getAnnotation(Table.class);
		if (table == null || table.name().equals(""))
			throw new Exception("get table name error, annotation not exist");
		tableName = table.name();
		return tableName;
	}

	/**
	 * 验证表字段 Annotation
	 * @param column 
	 * @return
	 */
	public boolean valdateTableColumnNullAndIncrement(TableColumn column)
	{
		if (column == null || column.increment() == true)
		{
			return false;
		}
		return true;
	}

	/**
	 * 验证参数默认值。
	 * @param obj
	 * @return
	 */
	public boolean valdateParamDefault(Object obj)
	{
		if (obj == null)
			return false;
		if (Integer.class.equals(obj.getClass()))
		{
			if ((Integer)obj == Constants.ENTITY_NULLITY_DEFAULT)
				return false;
		}
		if (Long.class.equals(obj.getClass()))
		{
			if ((Long)obj == Constants.ENTITY_NULLITY_DEFAULT)
				return false;
		}
		if (Float.class.equals(obj.getClass()))
		{
			if ((Float)obj == Constants.ENTITY_NULLITY_DEFAULT)
				return false;
		}
		if (Double.class.equals(obj.getClass()))
		{
			if ((Double)obj == Constants.ENTITY_NULLITY_DEFAULT)
				return false;
		}
		return true;
	}
	
	/**
	 * 获取查询参数
	 * @return
	 */
	public List<Object> getSelectParam()
	{
		lstParam = new ArrayList<Object>();
		Object value = null;

		for (Field f : fields)
		{
			if (null == f.getAnnotation(TableColumn.class))
				continue;
			value = BaseEntity.getter(obj, f.getName());
			if (!this.valdateParamDefault(value))
				continue;
			lstParam.add(value);
		}
		return lstParam;
	}

	/**
	 * 获取插入参数
	 * @return
	 */
	public List<Object> getInsertParam()
	{
		lstParam = new ArrayList<Object>();

		for (Field f : fields)
		{
			if (!this.valdateTableColumnNullAndIncrement(f.getAnnotation(TableColumn.class)))
				continue;
			lstParam.add(BaseEntity.getter(obj, f.getName()));
		}
		return lstParam;
	}

	/**
	 * 获取where主键条件
	 * @return
	 * @throws Exception
	 */
	public String getKeyWhere() throws Exception
	{
		StringBuffer sql = new StringBuffer();
		TableColumn column = null;
		String key = null;
		Object value = null;
		lstParam = new ArrayList<Object>();

		sql.append(" where ");
		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (column == null || !column.isKey())
				continue;
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();

			value = BaseEntity.getter(obj, f.getName());
			if (!this.valdateParamDefault(value))
				throw new Exception("key value is null");
			lstParam.add(value);
			sql.append(key).append(" = ").append("? ");
			break;
		}
		return sql.toString();
	}

	/**
	 * 获取查询语句。
	 * @return
	 */
	public String getSelectSql()
	{
		StringBuffer sql = new StringBuffer();
		TableColumn column = null;
		String key = null;

		sql.append(" where 1=1 ");
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

			if (!this.valdateParamDefault(BaseEntity.getter(obj, f.getName())))
				continue;
			sql.append(" and ").append(key).append(" = ? ");
		}
		return sql.toString();
	}
}
