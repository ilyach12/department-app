package com.chaikovsky.dao;

import com.chaikovsky.model.Employees;
import java.sql.Date;
import java.util.List;

public interface IEmployeesDao {

    List<Employees> findAll();
    List<Employees> findByBirthdayDate(Date birthday);
    List<Employees> findByBirthdayBetween(Date birthday, Date birthday1);
    void insertNewEmployee(String employeeName, Long department_id, Date birthday, int salary);
    void updateById(Long id, String employeeName, Long department_id, Date birthday, int salary);
    void deleteById(Long id);
}
