package test;


import org.junit.Test;
import util.jdbcUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class StudentDaoTest {


    /**
     * 事务
     * @throws SQLException
     */
    @Test
    public void testTrans() throws SQLException {
         PreparedStatement statement = null;
         Connection conn = null;
        try {
            conn = jdbcUtil.getConn();
            //设置不要自动提交事务
            conn.setAutoCommit(false);
            String sql = "select balance from t_bank where name = ? and balance >= ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, "罗");
            statement.setDouble(2, 400000);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.print("余额不足!");
            }

            String sql1 = "update t_bank set balance = balance - ? where name = ?";
            statement = conn.prepareStatement(sql1);
            statement.setDouble(1, 200000);
            statement.setString(2, "罗");
            statement.executeUpdate();

          //  int i = 10 / 0;

            String sql2 = "update t_bank set balance = balance + ? where name = ?";
            statement = conn.prepareStatement(sql2);
            statement.setDouble(1, 200000);
            statement.setString(2, "郑");
            statement.executeUpdate();
            //处理完成提交事务
            conn.commit();
            jdbcUtil.close(conn, statement,null);
        } catch (Exception e) {
            //事务回滚
             conn.rollback();
        }

    }

    /**
     * 批量处理
     */
    @Test
    public void  testBach(){
        long begin = System.currentTimeMillis();
        try {
            Connection conn = jdbcUtil.getConn();
            Statement statement = conn.createStatement();
            for (int i= 0;i<1000;i++){
                String sql = "INSERT  INTO  t_bank(balance) VALUE  ("+i+")";
                statement.addBatch(sql);
                if(i%200==0){
                    statement.executeBatch();
                    statement.clearBatch();

                }
            }
            System.out.print(System.currentTimeMillis()-begin);
        jdbcUtil.close(conn,statement,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  testBach2(){
        long begin = System.currentTimeMillis();
        try {
            String sql = "INSERT  INTO  t_bank(balance) VALUE (?)";
            Connection conn = jdbcUtil.getConn();
            PreparedStatement statement = conn.prepareStatement(sql);
            for (int i= 0;i<1000;i++){
                statement.setDouble(1,10+i);
                statement.addBatch();
                if(i%200 ==0){
                    statement.executeBatch();
                    statement.clearBatch();
                    statement.clearParameters();
                }
            }
            System.out.print(System.currentTimeMillis()-begin);
            jdbcUtil.close(conn,statement,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 二进制数据的存取
     */
       @Test
      public  void testBlob(){
           try {
               String sql = "INSERT  INTO  t_file(file) VALUE (?)";
               Connection conn = jdbcUtil.getConn();
               PreparedStatement statement = conn.prepareStatement(sql);
               statement.setBlob(1,new FileInputStream("C:/Users/user/Desktop/epc图片/01.jpg"));
               statement.execute();
               jdbcUtil.close(conn,statement,null);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

    @Test
    public  void testBlob2(){
        try {
            String sql = "select file from t_file where id = 1";
            Connection conn = jdbcUtil.getConn();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Blob file = resultSet.getBlob("file");
                InputStream binaryStream = file.getBinaryStream();
                Files.copy(binaryStream, Paths.get("d:/123.jpg"));

            }
              jdbcUtil.close(conn,statement,resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     @Test
    public void  testGetID(){

         try {
             String sql = "INSERT  INTO  t_file(file) VALUE (?)";
             Connection conn = jdbcUtil.getConn();
             PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
             statement.setBlob(1,new FileInputStream("C:/Users/user/Desktop/epc图片/01.jpg"));
             statement.executeUpdate();
             ResultSet keys = statement.getGeneratedKeys();
             while (keys.next()){
                 long id = keys.getLong(1);
                 System.out.print(id);
             }
             jdbcUtil.close(conn,statement,null);
         } catch (Exception e) {
             e.printStackTrace();
         }

     }

    @Test
    public void  testGetID2(){

        try {
            StringBuilder sb = new StringBuilder(100);
            sb.append("INSERT  INTO  t_file").append("(file) VALUE (").append(new FileInputStream("C:/Users/user/Desktop/epc图片/01.jpg")).append(")");
            String sql = sb.toString();
            Connection conn = jdbcUtil.getConn();
            Statement statement = conn.createStatement();

            statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = statement.getGeneratedKeys();
            while (keys.next()){
                long id = keys.getLong(1);
                System.out.print(id);
            }
            jdbcUtil.close(conn,statement,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
