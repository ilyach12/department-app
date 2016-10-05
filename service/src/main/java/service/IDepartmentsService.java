package service;

import model.Department;

import java.util.List;

public interface IDepartmentsService {

    List<Department> getAll();
    List<Department> getAllDepartmentsWithEmployees();
    List<Department> getDepartmentByNameWithEmployees(String departmentName);
    void update(Long id, String departmentName);
    void delete(String departmentName);
    void insert(String departmentName);
}
