package smis.dao;

import smis.domain.Student;

import java.util.List;

/**
 * 对学生类的增删改查
 */
public interface IStudentDAO {
    /**
     * 要新增的学生对象
     * @param stu
     */
    void add(Student stu);

    /**
     * 要删除的学生对象
     * @param id
     */
    void delete (Long id);
  /**
   * 更新学生数据
   */
    void update (Student newStu);

    /**
     *根据id 查询单个学生信息
     * @param id
     * @return
     */
    Student get(Long id);

    /**
     * 查询所有学生信息
     * @return
     */
    List<Student> stuList();
    /**
     * 查询学生总数
     * @return
     */
    Long  count();
}
