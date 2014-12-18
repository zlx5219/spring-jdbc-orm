package com.zlx.orm.dao.impl;

import org.springframework.stereotype.Repository;

import com.zlx.orm.dao.AppBookDao;
import com.zlx.orm.po.AppBook;

/**
 * 书DAO。
 * @author zlx
 */
@Repository("appBookDao")
public class AppBookDaoImpl extends BaseDaoImpl<AppBook> implements AppBookDao
{
}
