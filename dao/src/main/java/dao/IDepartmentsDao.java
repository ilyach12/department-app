package dao;

import model.Department;

import javax.sql.DataSource;
import java.util.List;

public interface IDepartmentsDao {

    void setDataSource(DataSource dataSource);
    List<Department> findAll();
    List<Department> findAllWithEmployees();
    void insertNewDepartment(String departmentName);
    void updateById(Long id, String departmentName);
    void deleteByName(String departmentName);
}
