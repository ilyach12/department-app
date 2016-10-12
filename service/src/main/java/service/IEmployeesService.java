package service;

import model.Employees;

import java.sql.Date;
import java.util.List;

public interface IEmployeesService {

    List<Employees> getAll();
    List<Employees> getEmployeesByBirthdayDate(Date birthday);
    List<Employees> getEmployeesByBirthdayDateBetween(Date birthday, Date birthday1);
    void insert(String employeeName, Long department_id, Date birthday, int salary);
    void update(Long id, String employeeName, Long department_id, Date birthday, int salary);
    void delete(Long id);
}
