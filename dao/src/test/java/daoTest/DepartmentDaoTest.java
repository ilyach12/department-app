package daoTest;

import dao.JdbcDepartmentsDao;
import daoTest.testConfig.DBContext;
import model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBContext.class})
@ActiveProfiles({"database"})
public class DepartmentDaoTest {

    @Autowired
    private JdbcDepartmentsDao departmentDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

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
    @DirtiesContext
    public void testUpdate(){
        departmentDao.updateById(2L, ".NET");

        String sql = "select * from department where id = 2";
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            Department department1 = new Department();
            department1.setId(rs.getLong("id"));
            department1.setDepartmentName(rs.getString("departmentName"));

            assertEquals(".NET", department1.getDepartmentName());
            return department1;
        });
    }

    @Test
    @DirtiesContext
    public void testInsert() {
        departmentDao.insertNewDepartment("Marketing");
        List<Department> departments = departmentDao.findAll();
        assertEquals(5, departments.size());
    }

    @Test
    @DirtiesContext
    public void testDelete(){
        departmentDao.deleteByName("HR");
        List<Department> departments = departmentDao.findAll();
        assertEquals(3, departments.size());
    }
}
