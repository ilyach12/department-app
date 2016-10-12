package dao;

import model.Employees;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

public interface IEmployeesDao {

    void setDataSource(DataSource dataSource);
    List<Employees> findAll();
    List<Employees> findByBirthdayDate(Date birthday);
    List<Employees> findByBirthdayBetween(Date birthday, Date birthday1);
    void insertNewEmployee(String employeeName, Long department_id, Date birthday, int salary);
    void updateById(Long id, String employeeName, Long department_id, Date birthday, int salary);
    void deleteById(Long id);
}
