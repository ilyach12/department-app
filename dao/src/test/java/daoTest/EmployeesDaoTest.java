package daoTest;

import dao.JdbcEmployeesDao;
import daoTest.testConfig.DBContext;
import model.Employees;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBContext.class})
@ActiveProfiles({"database"})
public class EmployeesDaoTest {

    @Autowired
    private JdbcEmployeesDao employeesDao;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


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
    public void testInsert(){
        employeesDao.insertNewEmployee("Arkadiy", 2L, Date.valueOf("1985-06-14"), 1250);
        List<Employees> allEmployees = employeesDao.findAll();
        assertEquals(12, allEmployees.size());
    }

    @Test
    public void testUpdate(){
        employeesDao.updateById(1L, "Julian", 3L, Date.valueOf("1995-08-11"), 1700);

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
    public void testDelete(){
        employeesDao.deleteById(2L);
        List<Employees> employees = employeesDao.findAll();
        assertEquals(11, employees.size());
    }
}
