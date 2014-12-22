package com.zlx.orm.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.annotation.TableColumn;

/**
 * 删除sql实现 **（谨慎使用，以免错删数据）**
 * @author zlx
 *
 * @param <T>
 */
public class DeleteQueryCombineSql<T> extends BaseCombineSql<T> implements CombineSql
{
	public DeleteQueryCombineSql(T obj)
	{
		super(obj.getClass());
		this.obj = obj;
	}

	@Override
	public String getSql() throws Exception
	{
		sql = new StringBuffer();
		sql.append("delete from ").append(this.getTableName()).append(" ").append(this.getKeyWhere());

		return sql.toString();
	}

	@Override
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

	@Override
	public void setSql(CombineSql query)
	{
		// TODO Auto-generated method stub
	}

	public String getWhere() throws Exception
	{
		StringBuffer sql = new StringBuffer();
		TableColumn column = null;
		String key = null;
		Object value = null;
		lstParam = new ArrayList<Object>();

		sql.append(" where 1=1 ");
		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (column == null)
				continue;
			key = f.getName();
			if (column.value() != null && !"".equals(column.value()))
				key = column.value();

			value = BaseEntity.getter(obj, f.getName());
			if (!this.valdateParamDefault(value))
				throw new Exception("key value is null");
			lstParam.add(value);
			sql.append(" and ").append(key).append(" = ").append("? ");
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
