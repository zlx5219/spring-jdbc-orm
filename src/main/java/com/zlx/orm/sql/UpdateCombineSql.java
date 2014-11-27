package com.zlx.orm.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.StringUtil;
import com.zlx.orm.annotation.TableColumn;

/**
 * 更新sql实现。
 * @author zlx
 *
 * @param <T>
 */
public class UpdateCombineSql<T> extends BaseCombineSql<T> implements
		CombineSql
{

	private List<Object> lstParam;

	public UpdateCombineSql(T obj)
	{
		super(obj.getClass());
		super.obj = obj;
		lstParam = new ArrayList<Object>();
	}

	public String getSql() throws Exception
	{
		sql = new StringBuffer();
		sql.append(" update ").append(this.getTableName()).append(" ")
				.append(this.getUpdateSql());
		sql.append(this.getKeyWhere());
		return sql.toString();
	}

	public List<Object> getParam()
	{
		lstParam = this.getUpdateParam();
		lstParam.add(this.getKeyParam());
		return lstParam;
	}

	public void setSql(CombineSql query)
	{
		// TODO Auto-generated method stub
	}

	public String toString()
	{
		try
		{
			return this.getSql();
		}
		catch (Exception e)
		{
		}
		return "-----";
	}

	public String getUpdateSql()
	{
		StringBuffer sql = new StringBuffer();
		TableColumn column = null;
		String key = null;
		int count = 0;
		Object value;

		sql.append(" set ");
		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (!this.valdateTableColumn(column) || column.isKey())
				continue;
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();

			value = BaseEntity.getter(obj, f.getName());
			if (!this.valdateParamDefault(value))
				continue;
			if (count > 0)
				sql.append(", ");
			sql.append(key).append(" = ? ");
			count++;
		}
		return sql.toString();
	}

	public Object getKeyParam()
	{
		TableColumn column = null;
		Object value = null;

		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (!this.valdateTableColumn(column) || !column.isKey())
				continue;
			value = BaseEntity.getter(obj, f.getName());
			break;
		}
		return value;
	}

	public List<Object> getUpdateParam()
	{
		lstParam = new ArrayList<Object>();
		Object value = null;
		TableColumn column = null;

		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (!this.valdateTableColumn(f.getAnnotation(TableColumn.class))
					|| column.isKey())
				continue;
			value = BaseEntity.getter(obj, f.getName());
			if (!this.valdateParamDefault(value))
				continue;
			lstParam.add(value);
		}
		return lstParam;
	}
}
