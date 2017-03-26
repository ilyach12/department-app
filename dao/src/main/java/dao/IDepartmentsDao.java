package dao;

import model.Department;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public interface IDepartmentsDao {

    List<Department> findAll();
    List<Department> findAllWithEmployees();
    void insertNewDepartment(String departmentName);
    void updateById(Long id, String departmentName);
    void deleteByName(String departmentName);
}
