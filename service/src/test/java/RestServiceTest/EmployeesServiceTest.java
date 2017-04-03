package RestServiceTest;

import RestServiceTest.serviceConfig.ServiceCtx;
import model.Employees;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.RestEmployeesService;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceCtx.class})
@ActiveProfiles({"service"})
public class EmployeesServiceTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private RestEmployeesService employeesService;

    @Test
    public void testGetAll(){
        List<Employees> employees = employeesService.getAll();
        assertEquals(9, employees.size());
    }

    @Test
    public void testGetEmployeesByBirthdayDate(){
        String birthday = "1997-08-11";
        List<Employees> employees = employeesService.getEmployeesByBirthdayDate(Date.valueOf(birthday));
        assertEquals(1, employees.size());
    }

    @Test
    public void testGetEmployeesByBirthdayDateBetween(){
        String birthday = "1992-01-01";
        String birthday1 = "1995-01-01";
        List<Employees> employees = employeesService.getEmployeesByBirthdayDateBetween(Date.valueOf(birthday),
                Date.valueOf(birthday1));
        assertEquals(2, employees.size());
    }

    @Test
    public void testUpdate(){
        employeesService.update(1L, "Julian", 3L, Date.valueOf("1995-08-11"), 1700);

        String sql = "select * from employees where id = 1";
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employees em = new Employees();
            em.setId(rs.getLong("id"));
            em.setFullName(rs.getString("fullName"));
            em.setBirthday(rs.getDate("birthday"));
            em.setSalary(rs.getInt("salary"));

            assertEquals("Julian", em.getFullName());
            assertEquals(Date.valueOf("1995-08-11"), em.getBirthday());
            assertEquals(1700, em.getSalary());
            return em;
        });
    }

    @Test
    public void testInsert(){
        employeesService.insert("Arkadiy", 2L, Date.valueOf("1985-06-14"), 1250);
        List<Employees> allEmployees = employeesService.getAll();
        assertEquals(10, allEmployees.size());
    }

    @Test
    public void testDelete(){
        employeesService.delete(2L);
        List<Employees> employees = employeesService.getAll();
        assertEquals(9, employees.size());
    }
}
