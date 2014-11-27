package com.zlx.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 非空字段，加上此注解的的实体字段，值不能为空 或 不能为默认值（Constants.ENTITY_NULLITY_DEFAULT -1）
 * @author zlx
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnNotNull
{
}
