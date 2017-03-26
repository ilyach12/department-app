package controllerTest;

import controller.DepartmentsController;
import controllerTest.testConfig.ControllerContext;
import model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerContext.class})
@WebAppConfiguration
@ActiveProfiles({"controller"})
public class DepartmentsControllerTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private DepartmentsController departmentsController;

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
    public void testUpdate(){
        departmentsController.updateDepartmentNameById(2L, ".NET");

        String sql = "select * from department where id = 2";
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            Department department = new Department();
            department.setId(rs.getLong("id"));
            department.setDepartmentName(rs.getString("departmentName"));

            assertEquals(".NET", department.getDepartmentName());
            return department;
        });
    }

    @Test
    public void testInsert(){
        departmentsController.insertNewDepartment("Marketing");
        List<Department> departments = departmentsController.getAllDepartments();
        assertEquals(4, departments.size());
    }

    @Test
    public void testDelete(){
        departmentsController.deleteDepartmentByName("HR");
        List<Department> departments = departmentsController.getAllDepartments();
        assertEquals(3, departments.size());
    }
}
