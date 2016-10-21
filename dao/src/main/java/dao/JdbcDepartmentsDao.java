package dao;

import model.Department;
import model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides access for the database. He annotated as repository this Indicates
 * that an annotated class is a "Repository", originally defined by Domain-Driven
 * Design (Evans, 2003) as "a mechanism for encapsulating storage, retrieval,
 * and search behavior which emulates a collection of objects".
 */
@Repository
@PropertySource("classpath:query.properties")
@ComponentScan("config")
public class JdbcDepartmentsDao implements IDepartmentsDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${query.findAllDepartments}")
    private String findAllDepartments;
    @Value("${query.findAllDepartmentsWithEmployees}")
    private String findAllDepartmentsWithEmployees;
    @Value("${query.findOneDepartmentWithEmployees}")
    private String findOneDepartmentWithEmployees;
    @Value("${query.insertNewDepartment}")
    private String insertNewDepartment;
    @Value("${query.updateDepartment}")
    private String updateDepartment;
    @Value("${query.deleteDepartment}")
    private String deleteDepartment;

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Department> findAll() {
        return jdbcTemplate.query(findAllDepartments, (rs, rowNum) -> {
            Department department = new Department();
            department.setId(rs.getLong("id"));
            department.setDepartmentName(rs.getString("departmentName"));
            department.setAverageSalary(rs.getInt("averageSalary"));
            return department;
        });
    }

    /**
     * Finds all departments and adds to the list of employees working in these departments.
     *
     * @return <tt>List</tt> of all departments with employees.
     */
    @Override
    public List<Department> findAllWithEmployees() {
        return jdbcTemplate.query(findAllDepartmentsWithEmployees, (rs) -> {
            Map<Long, Department> map = new HashMap<>();
            Department department;

            while (rs.next()){
                Long id = rs.getLong("department.id");
                department = map.get(id);

                if (department == null){
                    department = new Department();
                    department.setId(rs.getLong("department.id"));
                    department.setDepartmentName(rs.getString("departmentName"));
                    department.setEmployeesInThisDepartment(new ArrayList<>());
                    map.put(id, department);
                }

                Long employeesId = rs.getLong("employees.id");
                if (employeesId > 0){
                    Employees employees = new Employees();
                    employees.setId(employeesId);
                    employees.setFullName(rs.getString("fullName"));
                    employees.setBirthday(rs.getDate("birthday"));
                    employees.setSalary(rs.getInt("salary"));
                    department.getEmployeesInThisDepartment().add(employees);
                }
            }

            return new ArrayList<>(map.values());
        });
    }

    /**
     * Inserting new row into Department table taking department name and generate Id.
     * for inserted department.
     *
     * @param departmentName name of new department
     */
    @Override
    public void insertNewDepartment(String departmentName) {
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        jdbcTemplate.update(insertNewDepartment, map);
    }

    /**
     * Update department name in the Department table.
     *
     * @param id Id of the department that you want to updateById
     * @param departmentName is a new department name that will replace the old name
     */
    @Override
    public void updateById(Long id, String departmentName) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("departmentName", departmentName);
        jdbcTemplate.update(updateDepartment, map);
    }

    /**
     * Removes the row where the name of the card matches {@code departmentName} parameter.
     *
     * @param departmentName is a department name that will removed of Department table
     */
    @Override
    public void deleteByName(String departmentName) {
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        jdbcTemplate.update(deleteDepartment, map);
    }
}
