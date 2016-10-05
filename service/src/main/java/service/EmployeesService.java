package service;

import dao.JdbcEmployeesDao;
import model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * This class annotated as "Service" and he has role of protection layout
 * between {@code EmployeesController} controller and {@code JdbcEmployeesDao} data
 * access class.
 */
@Service
@ComponentScan("dao")
public class EmployeesService implements IEmployeesService {

    @Autowired
    private JdbcEmployeesDao jdbcEmployeesDao;

    @Override
    public List<Employees> getAll() {
        return jdbcEmployeesDao.findAll();
    }

    @Override
    public List<Employees> getEmployeesByBirthdayDate(Date birthday) {
        return jdbcEmployeesDao.findByBirthdayDate(birthday);
    }

    @Override
    public List<Employees> getEmployeesByDepartmentName(String departmentName) {
        return jdbcEmployeesDao.findByDepartmentName(departmentName);
    }

    @Override
    public List<Employees> getEmployeesByBirthdayDateBetween(Date birthday, Date birthday1) {
        return jdbcEmployeesDao.findByBirthdayBetween(birthday, birthday1);
    }

    @Override
    public void insert(String employeeName, String department, Date birthday, int salary) {
        jdbcEmployeesDao.insertNewEmployee(employeeName, department, birthday, salary);
    }

    @Override
    public void update(Long id, String employeeName, String department, Date birthday, int salary) {
        jdbcEmployeesDao.updateById(id, employeeName, department, birthday, salary);
    }

    @Override
    public void delete(Long id) {
        jdbcEmployeesDao.deleteById(id);
    }
}
