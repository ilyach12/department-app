package com.chaikovsky.ServiceTest;

import com.chaikovsky.ServiceTest.serviceConfig.ServiceCtx;
import com.chaikovsky.model.Department;
import com.chaikovsky.service.DepartmentsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceCtx.class})
@ActiveProfiles({"service"})
public class DepartmentsServiceTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private DepartmentsService departmentsService;

    @Test
    public void testGetAll(){
        List<Department> departments = departmentsService.getAll();
        assertEquals(3, departments.size());
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        List<Department> departments = departmentsService.getAllDepartmentsWithEmployees();
        assertEquals(4, departments.size());
    }

    @Test
    public void testUpdate(){
        departmentsService.update(2L, ".NET");
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
        departmentsService.insert("Marketing");
        List<Department> departments = departmentsService.getAll();
        assertEquals(4, departments.size());
    }

    @Test
    public void testDelete(){
        departmentsService.delete("HR");
        List<Department> departments = departmentsService.getAll();
        assertEquals(3, departments.size());
    }
}
