package com.chaikovsky.service;

import com.chaikovsky.dao.JdbcEmployeesDao;
import com.chaikovsky.model.Employees;
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
    public List<Employees> getEmployeesByBirthdayDateBetween(Date birthday, Date birthday1) {
        return jdbcEmployeesDao.findByBirthdayBetween(birthday, birthday1);
    }

    @Override
    public void insert(String employeeName, Long department_id, Date birthday, int salary) {
        jdbcEmployeesDao.insertNewEmployee(employeeName, department_id, birthday, salary);
    }

    @Override
    public void update(Long id, String employeeName, Long department_id, Date birthday, int salary) {
        jdbcEmployeesDao.updateById(id, employeeName, department_id, birthday, salary);
    }

    @Override
    public void delete(Long id) {
        jdbcEmployeesDao.deleteById(id);
    }
}
