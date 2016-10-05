package serviceTest;

import model.Employees;
import org.junit.Before;
import org.junit.Test;
import service.WebAppEmployeesService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeesServiceTest {

    private WebAppEmployeesService employeesService;
    private List<Employees> list = new ArrayList<>();

    @Before
    public void setUp(){
        Employees employees = new Employees();
        employees.setId(1L);
        employees.setFullName("Semen Slepakov");
        employees.setDepartment("Java");
        employees.setBirthday(Date.valueOf("1987-08-11"));
        employees.setSalary(1200);

        Employees employees1 = new Employees();
        employees1.setId(2L);
        employees1.setFullName("Arkadiy Pospeliy");
        employees1.setDepartment(".NET");
        employees1.setBirthday(Date.valueOf("1991-01-01"));
        employees1.setSalary(1100);

        list.add(employees);
        list.add(employees1);
    }

    @Test
    public void testGetAll(){
        employeesService = mock(WebAppEmployeesService.class);
        when(employeesService.getAll()).thenReturn(list);
        assertEquals(2, employeesService.getAll().size());
    }

    @Test
    public void testGetEmployeesByBirthdayDate(){
        employeesService = new WebAppEmployeesService();
        List<Employees> employees = employeesService.getEmployeesByBirthdayDate(Date.valueOf("1994-08-19"));
        assertEquals(1, employees.size());
    }

    @Test
    public void testGetEmployeesByBirthdayDateBetween(){
        employeesService = new WebAppEmployeesService();

        String birthday = "1987-01-01";
        String birthday1 = "1992-01-01";
        List<Employees> employees = employeesService.getEmployeesByBirthdayDateBetween(Date.valueOf(birthday),
                Date.valueOf(birthday1));
        assertEquals(5, employees.size());
    }

    @Test
    public void testGetEmployeesByDepartmentName(){
        employeesService = new WebAppEmployeesService();
        List<Employees> employees = employeesService.getEmployeesByDepartmentName("dotNOT");
        assertEquals(4, employees.size());
    }

    @Test
    public void testInsert(){
        employeesService = new WebAppEmployeesService();
        employeesService.insert("Evkakiy", "dotNOT", Date.valueOf("1974-02-17"), 700);

        List<Employees> employees = employeesService.getAll();
        assertEquals(17, employees.size());
        List<Employees> employeesBday = employeesService.getEmployeesByBirthdayDate(Date.valueOf("1974-02-17"));
        assertEquals(1, employeesBday.size());
    }

    @Test
    public void testUpdate(){
        employeesService = new WebAppEmployeesService();
        employeesService.update(54L, "Artemiy", "Java", Date.valueOf("1920-01-01"), 800);

        List<Employees> employeesBday = employeesService.getEmployeesByBirthdayDate(Date.valueOf("1920-01-01"));
        assertEquals(1, employeesBday.size());
    }

    @Test
    public void testDelete(){
        employeesService = new WebAppEmployeesService();
        employeesService.delete(54L);

        List<Employees> employees = employeesService.getAll();
        assertEquals(16, employees.size());
    }
}
