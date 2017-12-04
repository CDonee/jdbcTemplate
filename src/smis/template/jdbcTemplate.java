package smis.template;


import smis.util.druidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class jdbcTemplate {
    /**
     * dml处理
     *
     * @param sql
     * @param param
     */
    public static void update(String sql, Object... param) {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet set = null;

        try {
            conn = druidUtil.getConn();
            state = conn.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                state.setObject(i + 1, param[i]);
            }
            state.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            druidUtil.close(conn, state, set);
        }
    }

    public static <T> T query(String sql, IResultSetHandler<T> handler, Object... params) {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet set = null;

        try {
            conn = druidUtil.getConn();
            state = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    state.setObject(i + 1, params[i]);
                }
            }
            ResultSet resultSet = state.executeQuery(sql);
            return handler.handleResult(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            druidUtil.close(conn, state, set);

        }
        return null;
    }
}
