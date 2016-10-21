package daoTest;

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

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DepartmentDaoTest {

    private EmbeddedDatabase ds;
    private JdbcDepartmentsDao departmentDao = new JdbcDepartmentsDao();
    private JdbcTemplate template;

    @Before
    public void setUp() {
        ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .addScript("db/insert-data.sql")
                .build();

        departmentDao.setDataSource(ds);
        template = new JdbcTemplate(ds);

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
    public void testFindAll(){
        List<Department> departments = departmentDao.findAll();
        assertEquals(4, departments.size());
    }

    @Test
    public void testFindAllWithEmployees(){
        List<Department> departments = departmentDao.findAllWithEmployees();
        assertEquals(4, departments.size());
    }

    @Test
    public void testUpdate(){
        departmentDao.updateById(2L, ".NET");

        String sql = "select d.* from department as d where departmentName = '.NET'";
        template.query(sql, (rs, rowNum) -> {
            Department department1 = new Department();
            department1.setId(rs.getLong("id"));
            department1.setDepartmentName(rs.getString("departmentName"));

            assertEquals(".NET", department1.getDepartmentName());
            return department1;
        });
    }

    @Test
    public void testInsert(){
        departmentDao.insertNewDepartment("Marketing");
        List<Department> departments = departmentDao.findAll();
        assertEquals(5, departments.size());

        String sql = "select d.* from department as d where departmentName = 'Marketing'";
        template.query(sql, (rs, rowNum) -> {
            Department department1 = new Department();
            department1.setId(rs.getLong("id"));
            department1.setDepartmentName(rs.getString("departmentName"));

            assertEquals("Marketing", department1.getDepartmentName());
            return department1;
        });
    }

    @Test
    public void testDelete(){
        departmentDao.deleteByName("HR");
        List<Department> departments = departmentDao.findAll();
        assertEquals(3, departments.size());
    }

    @After
    public void tearDown() {
        ds.shutdown();
    }
}
