package com.zlx.orm;

/**
 * service统一抛出异常。
 * @author zlx
 *
 */
public class ServiceException extends Exception
{

	private static final long serialVersionUID = -7864470570086804876L;

	private int code;
	private String desc;

	public ServiceException()
	{
		super();
	}

	public ServiceException(Exception e)
	{
		super(e.getLocalizedMessage());
		this.desc = e.getLocalizedMessage();
	}

	public ServiceException(String desc)
	{
		this.desc = desc;
	}

	public ServiceException(int code, String desc)
	{
		super(desc);
		this.code = code;
		this.desc = desc;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"code\":\"");
		builder.append(code);
		builder.append("\", desc\":\"");
		builder.append(desc);
		builder.append("}");
		return builder.toString();
	}
}
