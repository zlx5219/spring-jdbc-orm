package com.zlx.orm.sql;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zlx.orm.StringUtil;
import com.zlx.orm.annotation.Table;
import com.zlx.orm.annotation.TableColumn;
import com.zlx.orm.expression.Expression;

/**
 * 自由拼装sql查询语句实现。
 * @author zlx
 *
 */
public class SelectQuery implements Serializable, CombineSql
{
	private static final long serialVersionUID = 8541922853046624474L;
	private List<Expression> lstExp;
	private List<Order> lstOrder;
	private Integer maxResults;
	private Integer firstResult;
	private String[] showColumns;
	private Class<?> objClass;
	private String tableName;
	private List<Object> lstParam;
	private boolean isCount = false;
	private StringBuffer baseSql;

	public SelectQuery(Class<?> objClass)
	{
		this.objClass = objClass;
		init();
	}

	/**
	 * 构造器
	 * @param objClass
	 * @param isCount
	 */
	public SelectQuery(Class<?> objClass, boolean isCount)
	{
		this.objClass = objClass;
		this.isCount = isCount;
		init();
	}
	
	public SelectQuery(Class<?> objClass, String...showColumns)
	{
		this.objClass = objClass;
		this.showColumns = showColumns;
		init();
	}

	private void init()
	{
		lstExp = new ArrayList<Expression>();
		lstOrder = new ArrayList<Order>();
	}

	public static SelectQuery newInstance(Class<?> objClass)
	{
		return new SelectQuery(objClass);
	}

	public static SelectQuery newInstance(Class<?> objClass, String...showColumns)
	{
		return new SelectQuery(objClass, showColumns);
	}

	public static SelectQuery newInstance(Class<?> objClass, boolean isCount)
	{
		return new SelectQuery(objClass, isCount);
	}

	public SelectQuery add(Expression exp)
	{
		if (exp != null)
			lstExp.add(exp);
		return this;
	}
	
	public SelectQuery add(Order order)
	{
		if (order != null)
			lstOrder.add(order);
		return this;
	}

	public SelectQuery add(Order... order)
	{
		if (order != null)
			lstOrder.addAll(Arrays.asList(order));
		return this;
	}

	public SelectQuery setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
		return this;
	}

	public SelectQuery setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
		return this;
	}

	private String getTableName() throws Exception
	{
		if (tableName != null && !"".equals(tableName))
			return tableName;
		Table table = objClass.getAnnotation(Table.class);
		if (table == null || table.name().equals(""))
			throw new Exception("get table name error, annotation not exist");
		tableName = table.name();
		return tableName;
	}

	private String getShowColumns()
	{
		if (this.isCount)
			return " count(*) ";
		if (this.showColumns == null || this.showColumns.length == 0)
			return " * ";
		StringBuffer strColumns = new StringBuffer();

		for (int n = 0; n < this.showColumns.length - 1; n++)
			strColumns.append(this.showColumns[n]).append(',');
		strColumns.append(this.showColumns[this.showColumns.length - 1]).append(' ');

		return strColumns.toString();
	}

	private StringBuffer getBaseSql(StringBuffer sql) throws Exception
	{
		if (this.baseSql != null)
		{
			sql.append(baseSql);
			return sql;
		}
		sql.append("select ").append(this.getShowColumns()).append(" from ").append(this.getTableName()).append(" where 1 = 1 ");
		return sql;
	}

	private StringBuffer getWhere(StringBuffer sql)
	{
		if (!lstExp.isEmpty())
		{
			for (int n = 0; n < lstExp.size(); n++)
				sql.append(" and ").append(lstExp.get(n).toSql()).append(' ');
		}
		return sql;
	}

	private StringBuffer getOrder(StringBuffer sql)
	{
		if (this.lstOrder.isEmpty())
			this.getDefaultOrder();
		if (this.lstOrder.isEmpty())
			return sql;

		sql.append(" order by ");
		for (int n = 0; n < lstOrder.size() - 1; n++)
			sql.append(lstOrder.get(n).toString()).append(',');
		sql.append(lstOrder.get(lstOrder.size() - 1)).append(' ');

		return sql;
	}

	private void getDefaultOrder()
	{
		Field[] fields = this.objClass.getDeclaredFields();

		TableColumn column = null;
		String key = null;
		lstParam = new ArrayList<Object>();

		for (Field f : fields)
		{
			column = f.getAnnotation(TableColumn.class);
			if (column == null || !column.defaultOrder())
				continue;
			key = f.getName();
			if (StringUtil.isNotEmpty(column.value()))
				key = column.value();

			this.add(new Order(key, column.ascending()));
		}
	}

	private StringBuffer getLimit(StringBuffer sql)
	{
		if (this.maxResults == 0 || this.maxResults <= this.firstResult)
			return sql;
		if (this.maxResults <= this.firstResult)
			return sql;

		sql.append(" limit ").append(this.firstResult).append(',').append(this.maxResults);
		return sql;
	}

	/**
	 * 获取sql语句。
	 * @return
	 * @throws Exception
	 */
	public String getSql() throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql = this.getBaseSql(sql);
		sql = this.getWhere(sql);
		sql = this.getOrder(sql);
		sql = this.getLimit(sql);

		return sql.toString();
	}

	public List<Object> getParam()
	{
		lstParam = new ArrayList<Object>();
		if (this.lstExp.isEmpty())
			return lstParam;
		for (Expression e : lstExp)
		{
			if (e.getParam() == null)
				continue;
			lstParam.addAll(e.getParam());
		}
		return lstParam;
	}

	public static class Order implements Serializable
	{
		private static final long serialVersionUID = -1839247829896198323L;
		private boolean ascending;
		private String propertyName;

		public Order(String propertyName, boolean ascending)
		{
			this.propertyName = propertyName;
			this.ascending = ascending;
		}

		public String toString() 
		{
			return propertyName + ' ' + (ascending?"asc":"desc");
		}

		/**
		 * Ascending order
		 *
		 * @param propertyName
		 * @return Order
		 */
		public static Order asc(String propertyName)
		{
			return new Order(propertyName, true);
		}

		/**
		 * Descending order
		 *
		 * @param propertyName
		 * @return Order
		 */
		public static Order desc(String propertyName)
		{
			return new Order(propertyName, false);
		}
	}

	public void setSql(CombineSql query) throws Exception
	{
		baseSql = new StringBuffer(query.getSql());
		this.lstParam = query.getParam();
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
		return tableName;
	}
}
