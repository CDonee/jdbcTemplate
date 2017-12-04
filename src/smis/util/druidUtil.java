package smis.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class druidUtil {

     private static Properties p = new Properties();
     private static DataSource  dataSource;

    static {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/db.properties");
            p.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(p);
          //  Class.forName(p.getProperty("driverClassName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  Connection getConn() {
        try {
            return  dataSource.getConnection();
           // return DriverManager.getConnection(p.getProperty("url"), p.getProperty("userName"), p.getProperty("passWord"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void close (Connection conn, Statement state, ResultSet set){
        try {
            if(set != null){
                set.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(state != null ){
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(conn != null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
