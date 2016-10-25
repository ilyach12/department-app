package serviceTest;

import dao.JdbcDepartmentsDao;
import model.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.util.ReflectionTestUtils;
import service.RestDepartmentsService;

import javax.servlet.ServletException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DepartmentsServiceTest {

    private EmbeddedDatabase ds;
    private JdbcTemplate template;
    private RestDepartmentsService departmentsService = new RestDepartmentsService();

    @Before
    public void setUp()throws ServletException {
        ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .addScript("db/insert-data.sql")
                .build();

        template = new JdbcTemplate(ds);
        JdbcDepartmentsDao departmentDao = new JdbcDepartmentsDao();
        departmentDao.setDataSource(ds);

        ReflectionTestUtils.setField(departmentsService, "departmentDao", departmentDao);
        ReflectionTestUtils.setField(departmentDao, "findAllDepartments", "select d.*, avg(e.salary) as averageSalary" +
                " from department as d left join employees as e on d.id = e.department_id group by d.id");
        ReflectionTestUtils.setField(departmentDao, "findAllDepartmentsWithEmployees", "select d.*, e.* from" +
                " department as d left join employees as e on d.id = e.department_id");
        ReflectionTestUtils.setField(departmentDao, "insertNewDepartment", "insert into department (departmentName)" +
                " values (:departmentName)");
        ReflectionTestUtils.setField(departmentDao, "updateDepartment", "update department set" +
                " departmentName = :departmentName where id=:id");
        ReflectionTestUtils.setField(departmentDao, "deleteDepartment", "delete from department" +
                " where lower(departmentName) = lower(:departmentName)");
    }

    @Test
    public void testGetAll(){
        List<Department> departments = departmentsService.getAll();
        assertEquals(4, departments.size());
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        List<Department> departments = departmentsService.getAllDepartmentsWithEmployees();
        assertEquals(4, departments.size());
    }

    @Test
    public void testUpdate(){
        departmentsService.update(2L, ".NET");
        String sql = "select d.* from department as d where departmentName = '.NET'";

        template.query(sql, (rs, rowNum) -> {
            Department department = new Department();
            department.setId(rs.getLong("id"));
            department.setDepartmentName(rs.getString("departmentName"));

            assertEquals(".NET", department.getDepartmentName());
            return department;
        });
    }

    @Test
    public void testInsert(){
        departmentsService.insert("Marketing");
        List<Department> departments = departmentsService.getAll();
        assertEquals(5, departments.size());

        String sql = "select d.* from department as d where departmentName = 'Marketing'";
        template.query(sql, (rs, rowNum) -> {
            Department department = new Department();
            department.setId(rs.getLong("id"));
            department.setDepartmentName(rs.getString("departmentName"));

            assertEquals("Marketing", department.getDepartmentName());
            return department;
        });
    }

    @Test
    public void testDelete(){
        departmentsService.delete("HR");
        List<Department> departments = departmentsService.getAll();
        assertEquals(3, departments.size());
    }

    @After
    public void tearDown() {
        ds.shutdown();
    }
}
