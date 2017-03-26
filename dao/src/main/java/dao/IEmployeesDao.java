package dao;

import model.Employees;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
