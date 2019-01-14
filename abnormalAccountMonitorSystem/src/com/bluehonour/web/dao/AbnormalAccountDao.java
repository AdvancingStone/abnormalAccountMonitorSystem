package com.bluehonour.web.dao;

public interface AbnormalAccountDao {
    //查询5分钟内的异常账户的个数
    public int getAccountNum();
}
