package com.chaikovsky.dao;

import com.chaikovsky.model.Department;
import java.util.List;

public interface IDepartmentsDao {

    List<Department> findAll();
    List<Department> findAllWithEmployees();
    void insertNewDepartment(String departmentName);
    void updateById(Long id, String departmentName);
    void deleteByName(String departmentName);
}
