package com.zlx.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表字段
 * @author zlx
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn
{
	/**
	 * 字段
	 * @return
	 */
	public String value() default "";

	/**
	 * 字段是否是自动增量 默认false
	 * @return
	 */
	public boolean increment() default false;

	/**
	 * 是否是主键。
	 * @return
	 */
	public boolean isKey() default false;

	/**
	 * 默认排序字段。
	 * @return
	 */
	public boolean defaultOrder() default false;

	/**
	 * 排序顺序。 true asc, false desc; default false;
	 * @return
	 */
	public boolean ascending() default false;
}
