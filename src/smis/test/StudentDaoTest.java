package smis.test;

import org.junit.Test;
import smis.dao.impl.StudentDaoImpl;
import smis.domain.Student;

import java.util.List;

public class StudentDaoTest {
    @Test
    public void testadd() {
        StudentDaoImpl studao = new StudentDaoImpl();
        Student student = new Student();
        student.setName("ttt地煞星666");
        student.setAge(26);
        studao.add(student);
    }

    @Test
    public void  testdelete() {
        StudentDaoImpl studao = new StudentDaoImpl();
        studao.delete(13L);
    }

    @Test
    public void testupdate() {
        StudentDaoImpl studao = new StudentDaoImpl();
        Student student = new Student();
        student.setId(1L);
        student.setName("眼前!");
        student.setAge(20);
        studao.update(student);
    }

    @Test
    public void testget() {
        StudentDaoImpl studao = new StudentDaoImpl();
        Student stu= studao.get(2L);
        System.out.println(stu.toString());
    }

    @Test
    public void teststuList() {
        StudentDaoImpl studao = new StudentDaoImpl();
        List<Student> students = studao.stuList();
        for (Student student : students) {
            System.out.println(student.toString());
        }
        System.out.println("students.size() = "+students.size()+"  共有学生 : "+studao.count()+"  人");

    }
}
