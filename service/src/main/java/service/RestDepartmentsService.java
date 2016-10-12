package service;

import dao.JdbcDepartmentsDao;
import model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class annotated as "Service" and he has role of protection layout
 * between {@code DepartmentController} controller and {@code JdbcDepartmentsDao} data
 * access class.
 */
@Service
@ComponentScan("dao")
public class RestDepartmentsService implements IDepartmentsService {

    @Autowired
    private JdbcDepartmentsDao departmentDao;

    @Override
    public List<Department> getAll(){
        return departmentDao.findAll();
    }

    @Override
    public List<Department> getAllDepartmentsWithEmployees() {
        return departmentDao.findAllWithEmployees();
    }

    @Override
    public void update(Long id, String departmentName){
        departmentDao.updateById(id, departmentName);
    }

    @Override
    public void delete(String departmentName){
        departmentDao.deleteByName(departmentName);
    }

    @Override
    public void insert(String departmentName){
        departmentDao.insertNewDepartment(departmentName);
    }
}
