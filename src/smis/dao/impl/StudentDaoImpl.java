package smis.dao.impl;

import smis.dao.IStudentDAO;
import smis.domain.Student;
import smis.template.IResultSetHandler;
import smis.template.jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对 Student类 数据进行增删改查
 */
public class StudentDaoImpl implements IStudentDAO {
    private Student stu;

    @Override
    public void add(Student stu) {

        String sql = "insert into t_student(name,age) values (?,?)";
        Object[] params = {stu.getName(),stu.getAge()};
        jdbcTemplate.update(sql,params);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from t_student where id = ?";
        Object[] params = {id};
        jdbcTemplate.update(sql,params);
    }

    @Override
    public void update(Student newStu) {
        String sql = "UPDATE t_student set name = ? ,age = ? where id = ?";
        Object[] params = {newStu.getName(),newStu.getAge(),newStu.getId()};
        jdbcTemplate.update(sql,params);

    }

    @Override
    public Student get(Long id) {
        String sql = "select * from t_student where id = ?";
        Object[] params = {id};
        List<Student> students = jdbcTemplate.query(sql, new StudentsResultHandler(), params);
        return  students.size()==1?students.get(0):null;
    }

    @Override
    public List<Student> stuList() {
        String sql = "select * from t_student ";
        return jdbcTemplate.query(sql, new StudentsResultHandler(), null);
    }

    @Override
    public Long count() {
        String sql = "select count(id) from t_student ";
        return jdbcTemplate.query(sql, new counResultsetHandler(), null);
    }


  class  counResultsetHandler implements  IResultSetHandler<Long>{
      @Override
      public Long handleResult(ResultSet set) {
          try {
              long aLong = -1L;
              if (set.next()){
                   aLong = set.getLong(1);
              }
              return aLong;
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return -1L;
      }
  }


    class  StudentsResultHandler  implements IResultSetHandler<List<Student>>{

        @Override
        public List<Student> handleResult(ResultSet set) {

            List<Student> list = new ArrayList<>();
            try {
                while (set.next()) {
                    Student stu = new Student();
                    stu.setId(set.getLong("id"));
                    stu.setName(set.getString("name"));
                    stu.setAge(set.getInt("age"));
                    list.add(stu);
                }
                return  list;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
