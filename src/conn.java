import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn {
    @Test
    public void testCreateTable()throws Exception{
        String sql = "create table t_student2 (id bigint(20) primary key auto_increment,name varchar(100),age int(10))";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1", "root", "admin");
        Statement stat = conn.createStatement();
        stat.execute(sql);
        stat.close();
        conn.close();
    }
    @Test
    public void testInsert() throws Exception {
        String sql = "insert into t_student(name,age) values('风清扬',22)";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///mydb1","root","admin");
        Statement state = conn.createStatement();
        state.execute(sql);
        state.close();
        conn.close();
    }

    @Test
    public void testUpDate() throws Exception {
        String sql = "update  t_student set age = 30 where id = 1";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///mydb1","root","admin");
        Statement state = conn.createStatement();
        state.execute(sql);
        state.close();
        conn.close();
    }

}
