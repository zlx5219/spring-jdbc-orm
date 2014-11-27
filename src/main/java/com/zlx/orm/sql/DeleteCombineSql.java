package com.zlx.orm.sql;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zlx.orm.StringUtil;
import com.zlx.orm.annotation.TableColumn;

/**
 * 删除sql实现
 * @author zlx
 *
 * @param <T>
 */
public class DeleteCombineSql<T> extends BaseCombineSql<T> implements CombineSql
{
	private Serializable id;
	public DeleteCombineSql(Class<?> objClass, Serializable id)
	{
		super(objClass);
		this.id = id;
	}

	public String getSql() throws Exception
	{
		sql = new StringBuffer();
		sql.append("delete from ").append(this.getTableName()).append(" ").append(this.getKeyWhere());

		return sql.toString();
	}

	public List<Object> getParam()
	{
		if (this.lstParam == null)
		{
			try
			{
				this.getSql();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return this.lstParam;
	}

	public void setSql(CombineSql query)
	{
		// TODO Auto-generated method stub
	}

	@Override
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
			if (!this.valdateTableColumn(column) || !column.isKey())
				continue;
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();

			value = id;
			if (!this.valdateParamDefault(value))
				throw new Exception("key value is null");
			lstParam.add(value);
			sql.append(key).append(" = ").append("? ");
			break;
		}
		return sql.toString();
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

}
