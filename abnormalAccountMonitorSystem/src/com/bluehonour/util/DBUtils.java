package com.bluehonour.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import clojure.main;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    /**
     * c3p0连接池
     */
    private static Connection conn;

    public static Connection getConn() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            conn = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//    	 Connection conn = DBUtils.getConn();
//         QueryRunner qr = new QueryRunner();
//         String sql = "select count(*) from abnormalAccount";
//         int accountNum = 0;
//         Object[] params = new Object[]{};
//         try {
//             accountNum = (int)(long) qr.query(conn,sql, new ScalarHandler());
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         System.out.println(accountNum);
//    }
}
