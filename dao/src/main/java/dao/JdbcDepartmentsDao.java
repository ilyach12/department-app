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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private List<Department> handlerForFindDepartmentsWithEmployees(ResultSet rs) throws SQLException{
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
                employees.setDepartment(rs.getString("department"));
                employees.setSalary(rs.getInt("salary"));
                department.getEmployeesInThisDepartment().add(employees);
            }
        }

        return new ArrayList<>(map.values());
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
     * Finding all departments and add to list {@code employeesInThisDepartment}
     * all, who has <tt>departmentId</tt> in Employees table equals
     * <tt>Id</tt> in Department table.
     *
     * @return <tt>List</tt> of all departments and <tt>List</tt> with all employees on
     * this departments.
     */
    @Override
    public List<Department> findAllWithEmployees() {
        return jdbcTemplate.query(findAllDepartmentsWithEmployees, this::handlerForFindDepartmentsWithEmployees);
    }

    /**
     * Fining one department by name of this department and all employees in result
     * department.
     *
     * @param departmentName this is a name of the sought department
     * @return one department and list of all employees within the sought department
     */
    @Override
    public List<Department> findDepartmentByNameWithEmployees(String departmentName) {
        Map<String, String> varMap = new HashMap<>();
        varMap.put("departmentName", departmentName);
        return jdbcTemplate.query(findOneDepartmentWithEmployees, varMap, this::handlerForFindDepartmentsWithEmployees);
    }

    /**
     * Inserting new row into Department table taking department name and generate Id
     * for new department.
     *
     * @param departmentName name of new insertable department
     */
    @Override
    public void insertNewDepartment(String departmentName) {
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        jdbcTemplate.update(insertNewDepartment, map);
    }

    /**
     * Updating department name in the Department table.
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
     * Deleting row where department name compiles {@code departmentName} param
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
