package dao;

import model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class working with database. He annotated as repository this Indicates
 * that an annotated class is a "Repository", originally defined by Domain-Driven
 * Design (Evans, 2003) as "a mechanism for encapsulating storage, retrieval,
 * and search behavior which emulates a collection of objects".
 */
@Repository
@PropertySource("classpath:query.properties")
@ComponentScan("config")
public class JdbcEmployeesDao implements IEmployeesDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${query.findAllEmployees}")
    private String findAllEmployees;
    @Value("${query.findByBirthdayDate}")
    private String findByBirthdayDate;
    @Value("${query.findByBirthdayDateBetween}")
    private String findByBirthdayDateBetween;
    @Value("${query.insertNewEmployee}")
    private String insertNewEmployee;
    @Value("${query.updateEmployee}")
    private String updateEmployee;
    @Value("${query.deleteEmployee}")
    private String deleteEmployee;

    /**
     * The {@code setDataSource} method is the data access layer`s initialization method.
     * The {@code SimpleJdbcInsert} classes provide a simplified configuration by taking advantage of
     * database metadata that can be retrieved through the JDBC driver. Simply created a new instance
     * and set the table name using the {@code withTableName} method and specify the name of the
     * generated key column with the {@code usingGeneratedKeyColumns} method.
     *
     * @param dataSource autowired of DataBaseConfig.class who located in config package
     */
    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private Employees setEmployeeFields(ResultSet rs, int rowNum) throws SQLException {
        Employees employees = new Employees();
        employees.setId(rs.getLong("id"));
        employees.setDepartmentName(rs.getString("departmentName"));
        employees.setFullName(rs.getString("fullName"));
        employees.setBirthday(rs.getDate("birthday"));
        employees.setSalary(rs.getInt("salary"));
        return employees;
    }

    @Override
    public List<Employees> findAll() {
        return jdbcTemplate.query(findAllEmployees, this::setEmployeeFields);
    }

    /**
     * {@code findByBirthday} takes parameter {@code birthday} of type sql.Date
     * and finds all employees who has field Birthday equals this parameter.
     *
     * @param birthday of type java.sql.Date
     * @return List of all found employees
     */
    @Override
    public List<Employees> findByBirthdayDate(Date birthday) {
        Map<String, Date> map = new HashMap<>();
        map.put("birthday", birthday);
        return jdbcTemplate.query(findByBirthdayDate, map, this::setEmployeeFields);
    }


    /**
     * {@code findByBirthdayBetween} takes two parameters like {@code findByBirthday} but
     * finds employees who has birthdays between this dates.
     *
     * @param birthday first date of birthday
     * @param birthday1 second date of birthday
     * @return List of all found employees
     */
    @Override
    public List<Employees> findByBirthdayBetween(Date birthday, Date birthday1) {
        Map<String, Date> map = new HashMap<>(2);
        map.put("birthday", birthday);
        map.put("birthday1", birthday1);
        return jdbcTemplate.query(findByBirthdayDateBetween, map, this::setEmployeeFields);
    }

    /**
     * Inserting new row into Employees table taking employee name and generate Id
     * for new employee.
     *
     * @param employeeName new employee name
     * @param department_id id of department where will working new employee
     * @param birthday birthday date of new employee
     * @param salary employee salary
     */
    @Override
    public void insertNewEmployee(String employeeName, Long department_id, Date birthday, int salary) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("fullName", employeeName);
        map.put("department_id", department_id);
        map.put("birthday", birthday);
        map.put("salary", salary);
        jdbcTemplate.update(insertNewEmployee, map);
    }

    /**
     * Updating employee data in the Employees table by employee id.
     *
     * @param id id of editable employee
     * @param employeeName new employee name
     * @param department_id new department ID
     * @param birthday new birthday date
     * @param salary new salary for this employee
     */
    @Override
    public void updateById(Long id, String employeeName, Long department_id, Date birthday, int salary) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("id", id);
        map.put("fullName", employeeName);
        map.put("department_id", department_id);
        map.put("birthday", birthday);
        map.put("salary", salary);
        jdbcTemplate.update(updateEmployee, map);
    }

    /**
     * Deleting row by id where id compiles {@code id} param.
     *
     * @param id is a id of employee that will removed from Employees table
     */
    @Override
    public void deleteById(Long id) {
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        jdbcTemplate.update(deleteEmployee, map);
    }
}
