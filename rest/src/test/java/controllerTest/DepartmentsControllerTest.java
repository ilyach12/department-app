package controllerTest;

import controller.DepartmentsController;
import dao.JdbcDepartmentsDao;
import model.Department;
import model.Employees;
import service.DepartmentsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DepartmentsControllerTest {

    private EmbeddedDatabase ds;
    private JdbcTemplate template;
    private DepartmentsController departmentsController;

    @Before
    public void setUp(){
        ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .addScript("db/insert-data.sql")
                .build();

        DepartmentsService departmentsService = new DepartmentsService();
        departmentsController = new DepartmentsController();
        JdbcDepartmentsDao departmentDao = new JdbcDepartmentsDao();
        template = new JdbcTemplate(ds);
        departmentDao.setDataSource(ds);

        ReflectionTestUtils.setField(departmentsService, "departmentDao", departmentDao);
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ReflectionTestUtils.setField(departmentDao, "findAllDepartments", "select d.id, d.departmentName, avg(e.salary) " +
                "as averageSalary from department as d left join employees as e on " +
                "d.departmentName = e.department group by e.department");
        ReflectionTestUtils.setField(departmentDao, "findAllDepartmentsWithEmployees", "select d.id, d.departmentName," +
                " e.id, e.fullName, e.department, e.birthday, e.salary from department as d left join employees as e on" +
                " d.departmentName = e.department");
        ReflectionTestUtils.setField(departmentDao, "findOneDepartmentWithEmployees", "select d.id, d.departmentName," +
                " e.id, e.fullName, e.department, e.birthday, e.salary from department as d left join employees as e" +
                " on d.departmentName = e.department where lower(d.departmentName) = lower(:departmentName)");
        ReflectionTestUtils.setField(departmentDao, "insertNewDepartment", "insert into department (departmentName)" +
                " values (:departmentName)");
        ReflectionTestUtils.setField(departmentDao, "updateDepartment", "update department set" +
                " departmentName = :departmentName where id=:id");
        ReflectionTestUtils.setField(departmentDao, "deleteDepartment", "delete from department" +
                " where lower(departmentName) = lower(:departmentName)");
    }

    private List<Department> testsHandler(ResultSet rs) throws SQLException {
        Map<Long, Department> map = new HashMap<>();
        Department department1 = null;
        while (rs.next()){
            Long id = rs.getLong("department.id");
            department1 = map.get(id);

            if (department1 == null){
                department1 = new Department();
                department1.setId(rs.getLong("department.id"));
                department1.setDepartmentName(rs.getString("departmentName"));
                department1.setEmployeesInThisDepartment(new ArrayList<>());
                map.put(id, department1);
            }

            Long employeesId = rs.getLong("employees.id");
            if (employeesId > 0){
                Employees employees = new Employees();
                employees.setId(employeesId);
                employees.setFullName(rs.getString("fullName"));
                employees.setBirthday(rs.getDate("birthday"));
                employees.setDepartment(rs.getString("department"));
                employees.setSalary(rs.getInt("salary"));
                department1.getEmployeesInThisDepartment().add(employees);
            }
        }
        assertEquals(3, department1.getEmployeesInThisDepartment().size());
        return new ArrayList<>(map.values());
    }

    @Test
    public void testGetAllDepartments(){
        List<Department> departments = departmentsController.getAllDepartments();
        assertEquals(4, departments.size());
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        List<Department> departments = departmentsController.getAllDepartmentsWithEmployees();
        assertEquals(4, departments.size());
    }

    @Test
    public void testGetDepartmentByNameWithEmployees(){
        List<Department> departments = departmentsController.getDepartmentByNameWithEmployees("dotNOT");
        assertEquals(1, departments.size());

        String sql = "select d.id, d.departmentName, e.id, e.fullName, e.department, e.birthday" +
                ", e.salary from department as d left join employees as e on d.departmentName = e.department " +
                "where lower(d.departmentName) = lower('dotNOT')";
        template.query(sql, this::testsHandler);
    }

    @Test
    public void testUpdate(){
        departmentsController.updateDepartmentNameById(2L, ".NET");
        List<Department> department = departmentsController.getDepartmentByNameWithEmployees(".NET");

        String sql = "select id, departmentName from department where departmentName = '.NET'";
        template.query(sql, (rs, rowNum) -> {
            Department department1 = new Department();
            department1.setId(rs.getLong("id"));
            department1.setDepartmentName(rs.getString("departmentName"));

            assertEquals(".NET", department1.getDepartmentName());
            return department;
        });
    }

    @Test
    public void testInsert(){
        departmentsController.insertNewDepartment("Marketing");
        List<Department> department = departmentsController.getDepartmentByNameWithEmployees("Marketing");
        List<Department> departments = departmentsController.getAllDepartments();
        assertEquals(5, departments.size());

        String sql = "select id, departmentName from department where departmentName = 'Marketing'";
        template.query(sql, (rs, rowNum) -> {
            Department department1 = new Department();
            department1.setId(rs.getLong("id"));
            department1.setDepartmentName(rs.getString("departmentName"));

            assertEquals("Marketing", department1.getDepartmentName());
            return department;
        });
    }

    @Test
    public void testDelete(){
        departmentsController.deleteDepartmentByName("HR");
        List<Department> departments = departmentsController.getAllDepartments();
        List<Department> department = departmentsController.getDepartmentByNameWithEmployees("HR");
        assertEquals(3, departments.size());
        assertEquals(0, department.size());
    }

    @After
    public void tearDown() {
        ds.shutdown();
    }
}
