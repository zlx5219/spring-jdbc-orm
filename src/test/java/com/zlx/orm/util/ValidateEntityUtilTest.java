package com.zlx.orm.util;

import java.util.Date;

import org.junit.Test;

import com.zlx.orm.ServiceException;
import com.zlx.orm.annotation.ColumnNotNull;

public class ValidateEntityUtilTest
{
	@Test
  	public void testValidate()
  	{
  		try
		{
  			System.out.println(ValidateEntityUtil.validate(new PersonTest("name", 1), -1)); // 成功返回
			ValidateEntityUtil.validate(new PersonTest(), -1); // 字段为空
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
	}

  	public static class PersonTest
  	{
  		@ColumnNotNull
  		private String name;
  		@ColumnNotNull
  		private int age = -1;
  		private Date time = new Date(System.currentTimeMillis());
  		private float size;

  		public PersonTest()
  		{
  			
  		}

  		public PersonTest(String name, int age)
  		{
  			this.name = name;
  			this.age = age;
  		}
  
  		public String getName()
  		{
  			return name;
  		}
  		public void setName(String name)
  		{
  			this.name = name;
  		}
  		public int getAge()
  		{
  			return age;
  		}
  		public void setAge(int age)
  		{
  			this.age = age;
  		}
  		public Date getTime()
  		{
  			return time;
  		}
  		public void setTime(Date time)
  		{
  			this.time = time;
  		}
  		public float getSize()
  		{
  			return size;
  		}
  		public void setSize(float size)
  		{
  			this.size = size;
  		}
  	}
}
