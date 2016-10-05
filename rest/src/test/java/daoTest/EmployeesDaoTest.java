package daoTest;

import model.Employees;
import dao.JdbcEmployeesDao;
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

public class EmployeesDaoTest {

    private EmbeddedDatabase ds;
    private JdbcEmployeesDao employeesDao;
    private JdbcTemplate template;

    @Before
    public void setUp() {
        ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .addScript("db/insert-data.sql")
                .build();
        employeesDao = new JdbcEmployeesDao();
        employeesDao.setDataSource(ds);
        template = new JdbcTemplate(ds);

        ReflectionTestUtils.setField(employeesDao, "findAllEmployees", "select id, fullName, department, birthday, salary from employees");
        ReflectionTestUtils.setField(employeesDao, "findByBirthdayDate", "select id, fullName, department, birthday, salary from employees where birthday = :birthday");
        ReflectionTestUtils.setField(employeesDao, "findByBirthdayDateBetween", "select id, fullName, department, birthday, salary from employees where birthday >= :birthday and birthday <= :birthday1");
        ReflectionTestUtils.setField(employeesDao, "findEmployeesByDepartmentName", "select id, fullName, department, birthday, salary from employees where lower(department) = lower(:department)");
        ReflectionTestUtils.setField(employeesDao, "insertNewEmployee", "insert into employees (fullName, department, birthday, salary) values(:fullName, :department, :birthday, :salary)");
        ReflectionTestUtils.setField(employeesDao, "updateEmployee", "update employees set fullName = :fullName, department = :department, birthday = :birthday, salary = :salary where id = :id");
        ReflectionTestUtils.setField(employeesDao, "deleteEmployee", "delete from employees where id = :id");
    }

    @Test
    public void testFindAll(){
        List<Employees> employees = employeesDao.findAll();
        assertEquals(12, employees.size());
    }

    @Test
    public void testFindByBirthdayDate(){
        String birthday = "1997-08-11";
        List<Employees> employees = employeesDao.findByBirthdayDate(Date.valueOf(birthday));
        assertEquals(1, employees.size());
    }

    @Test
    public void testFindByBirthdayBetween(){
        String birthday = "1992-01-01";
        String birthday1 = "1995-01-01";
        List<Employees> employees = employeesDao.findByBirthdayBetween(Date.valueOf(birthday), Date.valueOf(birthday1));
        assertEquals(4, employees.size());
    }

    @Test
    public void testFindByDepartmentName(){
        List<Employees> employees = employeesDao.findByDepartmentName("dotNOT");
        assertEquals(3, employees.size());
    }

    @Test
    public void testInsert(){
        employeesDao.insertNewEmployee("Arkadiy", "Java", Date.valueOf("1985-06-14"), 1250);
        List<Employees> allEmployees = employeesDao.findAll();
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
        employeesDao.updateById(1L, "Julian", "dotNOT", Date.valueOf("1995-08-11"), 1700);

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
        employeesDao.deleteById(2L);
        List<Employees> employees = employeesDao.findAll();
        assertEquals(11, employees.size());
    }

    @After
    public void tearDown() {
        ds.shutdown();
    }
}
