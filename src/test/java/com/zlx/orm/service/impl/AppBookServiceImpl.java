package com.zlx.orm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlx.orm.dao.AppBookDao;
import com.zlx.orm.po.AppBook;
import com.zlx.orm.service.AppBookService;

@Service("appBookService")
public class AppBookServiceImpl extends BaseServiceImpl<AppBook> implements AppBookService
{
	@SuppressWarnings("unused")
	private AppBookDao appBookDao;
	@Autowired
	public AppBookServiceImpl(AppBookDao appBookDao)
	{
		super(appBookDao);
		this.appBookDao = appBookDao;
	}
}
