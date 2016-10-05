package controllerTest;

import controller.EmployeesController;
import dao.JdbcEmployeesDao;
import model.Employees;
import service.RestEmployeesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeesControllerTest {

    private EmbeddedDatabase ds;
    private JdbcTemplate template;
    private EmployeesController employeesController;

    @Before
    public void setUp() {
        ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .addScript("db/insert-data.sql")
                .build();

        employeesController = new EmployeesController();
        RestEmployeesService employeesService = new RestEmployeesService();
        JdbcEmployeesDao employeesDao = new JdbcEmployeesDao();
        employeesDao.setDataSource(ds);
        template = new JdbcTemplate(ds);

        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        ReflectionTestUtils.setField(employeesService, "jdbcEmployeesDao", employeesDao);
        ReflectionTestUtils.setField(employeesDao, "findAllEmployees", "select id, fullName, department, birthday, salary from employees");
        ReflectionTestUtils.setField(employeesDao, "findByBirthdayDate", "select id, fullName, department, birthday, salary from employees where birthday = :birthday");
        ReflectionTestUtils.setField(employeesDao, "findByBirthdayDateBetween", "select id, fullName, department, birthday, salary from employees where birthday >= :birthday and birthday <= :birthday1");
        ReflectionTestUtils.setField(employeesDao, "findEmployeesByDepartmentName", "select id, fullName, department, birthday, salary from employees where lower(department) = lower(:department)");
        ReflectionTestUtils.setField(employeesDao, "insertNewEmployee", "insert into employees (fullName, department, birthday, salary) values(:fullName, :department, :birthday, :salary)");
        ReflectionTestUtils.setField(employeesDao, "updateEmployee", "update employees set fullName = :fullName, department = :department, birthday = :birthday, salary = :salary where id = :id");
        ReflectionTestUtils.setField(employeesDao, "deleteEmployee", "delete from employees where id = :id");
    }

    @Test
    public void testGetAllEmployees(){
        List<Employees> employees = employeesController.getAllEmployees();
        assertEquals(12, employees.size());
    }

    @Test
    public void testGetEmployeesByBirthdayDate(){
        String birthday = "1997-08-11";
        List<Employees> employees = employeesController.getEmployeesByBirthdayDate(Date.valueOf(birthday));
        assertEquals(1, employees.size());
    }

    @Test
    public void testGetEmployeesByDepartmentName(){
        List<Employees> employees = employeesController.getEmployeesByDepartmentName("Java");
        assertEquals(3, employees.size());
    }

    @Test
    public void testGetEmployeesByBirthdayDateBetween(){
        String birthday = "1992-01-01";
        String birthday1 = "1995-01-01";
        List<Employees> employees = employeesController.getEmployeesByBirthdayDateBetween(Date.valueOf(birthday),
                Date.valueOf(birthday1));
        assertEquals(4, employees.size());
    }

    @Test
    public void testInsert(){
        employeesController.insertNewEmployee("Arkadiy", "Java", Date.valueOf("1985-06-14"), 1250);
        List<Employees> allEmployees = employeesController.getAllEmployees();
        assertEquals(13, allEmployees.size());

        String sql = "select id, fullName, department, birthday, salary " +
                "from employees where fullName = 'Arkadiy'";
        template.query(sql, (rs, rowNum) -> {
            Employees em = new Employees();
            em.setId(rs.getLong("id"));
            em.setFullName(rs.getString("fullName"));
            em.setDepartment(rs.getString("department"));
            em.setBirthday(rs.getDate("birthday"));
            em.setSalary(rs.getInt("salary"));

            assertEquals("Java", em.getDepartment());
            assertEquals("Arkadiy", em.getFullName());
            assertEquals(Date.valueOf("1985-06-14"), em.getBirthday());
            assertEquals(1250, em.getSalary());
            return em;
        });
    }

    @Test
    public void testUpdate(){
        employeesController.updateEmployeeDataById(1L, "Julian", "dotNOT", Date.valueOf("1995-08-11"), 1700);

        String sql = "select id, fullName, department, birthday, salary " +
                "from employees where fullName = 'Julian'";
        template.query(sql, (rs, rowNum) -> {
            Employees em = new Employees();
            em.setId(rs.getLong("id"));
            em.setFullName(rs.getString("fullName"));
            em.setDepartment(rs.getString("department"));
            em.setBirthday(rs.getDate("birthday"));
            em.setSalary(rs.getInt("salary"));

            assertEquals("Julian", em.getFullName());
            assertEquals("dotNOT", em.getDepartment());
            assertEquals(Date.valueOf("1995-08-11"), em.getBirthday());
            assertEquals(1700, em.getSalary());
            return em;
        });
    }

    @Test
    public void testDelete(){
        employeesController.deleteEmployeeById(2L);
        List<Employees> employees = employeesController.getAllEmployees();
        assertEquals(11, employees.size());
    }

    @After
    public void tearDown() {
        ds.shutdown();
    }
}
