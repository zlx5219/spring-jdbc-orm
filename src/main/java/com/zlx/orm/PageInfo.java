package com.zlx.orm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息
 * @author zlx
 */
public class PageInfo implements Serializable
{
	private static final long serialVersionUID = -1948360974684233286L;

	/**
	 * 每页显示记录数。
	 */
	private int pageNum;

	/**
	 * 当前页。
	 */
	private int currentPage;

	/**
	 * 总页数。
	 */
	private int pageCount;

	/**
	 * 总记录数。
	 */
	private int totalRows;

	/**
	 * 页码列表大小，默认5 
	 */
	private int pageListSize = 5;
	/**
	 * 页码。
	 */
	private List<Integer> lstPage;

	public PageInfo(int totalRows, int currentPage, int pageNum)
	{
		this.totalRows = totalRows;
		this.currentPage = currentPage;
		this.pageNum = pageNum;
		this.setPageCount();
		this.setLstPage();
	}

	public int getPageNum()
	{
		return pageNum;
	}
	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}
	public int getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
	public int getPageCount()
	{
		return pageCount;
	}
	public void setPageCount()
	{
		if (totalRows % pageNum == 0)
			this.pageCount = totalRows / pageNum;
		else
			this.pageCount = (totalRows / pageNum) + 1;
	}
	public int getTotalRows()
	{
		return totalRows;
	}
	public void setTotalRows(int totalRows)
	{
		this.totalRows = totalRows;
	}
	public int getPageListSize()
	{
		return pageListSize;
	}
	public void setPageListSize(int pageListSize)
	{
		this.pageListSize = pageListSize;
	}
	public List<Integer> getLstPage()
	{
		return lstPage;
	}
	public void setLstPage()
	{
		this.lstPage = new ArrayList<Integer>();// .removeAll(this.pageNumList);// 设置之前先清空
		int totalPage = this.getPageCount();
		if (totalPage > this.pageListSize)
		{
			int halfSize = this.pageListSize / 2;
			int first = 1;
			int end = 1;
			if (this.currentPage - halfSize < 1)
			{ // 当前页靠近最小数1
				first = 1;
				end = this.pageListSize;
			}
			else if (totalPage - this.currentPage < halfSize)
			{ // 当前页靠近最大数
				first = totalPage - this.pageListSize + 1;
				end = totalPage;
			}
			else
			{
				first = this.currentPage - halfSize;
				end = this.currentPage + halfSize;
			}
			for (int i = first; i <= end; i++)
			{
				this.lstPage.add(i);
			}
		}
		else
		{
			for (int i = 0; i < totalPage; i++)
			{
				this.lstPage.add(i + 1);
			}
		}
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"pageNum\":\"");
		builder.append(pageNum);
		builder.append("\", currentPage\":\"");
		builder.append(currentPage);
		builder.append("\", pageCount\":\"");
		builder.append(pageCount);
		builder.append("\", totalRows\":\"");
		builder.append(totalRows);
		builder.append("\", pageListSize\":\"");
		builder.append(pageListSize);
		builder.append("\", lstPage\":\"");
		builder.append(lstPage);
		builder.append("}");
		return builder.toString();
	}
}
