package com.zlx.orm.util;

import java.lang.reflect.Field;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.ServiceException;
import com.zlx.orm.annotation.ColumnNotNull;


/**
 * 验证 Entity 字段的工具类，验证条件必须是 Entity的字段使用了 ColumnNotNull 这个注解
 * @author zlx
 */
public class ValidateEntityUtil
{
	/**
	 * 验证 Entity 的必填字段。
	 * 建议在插入时使用
	 * @param obj
	 * @param nullityValue 基础数据类型的无效值
	 * @return
	 * @throws Exception
	 */
	public static boolean validate(Object obj, long nullityValue) throws ServiceException
	{
		Field [] fields = obj.getClass().getDeclaredFields();
		ColumnNotNull column;
		Object value;
		Class<? extends Object> c;
		Double doubleNullity = new Double(nullityValue);

		for (Field f : fields)
		{
			column = f.getAnnotation(ColumnNotNull.class);
			if (column == null)
				continue;
			value = BaseEntity.getter(obj, f.getName());
			if (value == null)
			{
				throw new ServiceException("field is empty : field name is " + f.getName());
			}
			c = value.getClass();
			if (isPrimitive(c))
			{
				if (Double.valueOf(value.toString()).equals(doubleNullity))
					throw new ServiceException("field is nullity : field name is " + f.getName() + ", value is " + value);
			}
		}
		return true;
	}

	/**
	 * 判断是否是基础数据类型，基础数据类型包括（Byte，Short，Integer，Long，Float，Double）
	 * 如果是基础数据类型 返回true 不是则返回 false
	 * @param c
	 * @return
	 */
	public static boolean isPrimitive(Class<? extends Object> c)
	{
		if (c.equals(Integer.class) || c.equals(Long.class)
				|| c.equals(Double.class) || c.equals(Float.class)
				|| c.equals(Short.class) || c.equals(Byte.class))
			return true;
		return false;
	}
}
