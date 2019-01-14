package com.bluehonour.web.service;


public interface AbnormalAccountServiceDao {
	/**
	 * 得到5分钟的异常账号数量
	 * @return
	 */
	public int getAccountNum();
}
