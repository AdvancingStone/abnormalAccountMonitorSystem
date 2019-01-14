package com.bluehonour.web.dao.impl;

import com.bluehonour.util.DBUtils;
import com.bluehonour.web.dao.AbnormalAccountDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class AbnormalAccountDaoImpl implements AbnormalAccountDao {

    @Override
    public int getAccountNum() {
        Connection conn = DBUtils.getConn();
        QueryRunner qr = new QueryRunner();
        String sql = "select count(*) from abnormalAccount";
        int accountNum = 0;
        Object[] params = new Object[]{};
        try {
            accountNum = (int)(long) qr.query(conn,sql, new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountNum;
    }
}
