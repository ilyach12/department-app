package controllerTest;

import controller.EmployeesController;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import service.WebAppEmployeesService;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class EmployeesControllerTest {

    private EmployeesController employeesController;
    private WebAppEmployeesService employeesService;

    @Test
    public void testGetAll(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        ModelAndView mav = employeesController.getAll();
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testGetEmployeesByBirthdayDate(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);

        String birthday = "1997-08-11";
        ModelAndView mav = employeesController.getEmployeesByBirthdayDate(Date.valueOf(birthday));
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testGetEmployeesByBirthdayDateBetween(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);

        String birthday = "1993-01-01";
        String birthday1 = "1997-01-01";
        ModelAndView mav = employeesController.getEmployeesByBirthdayDateBetween(Date.valueOf(birthday),
                Date.valueOf(birthday1));
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testGetEmployeesByDepartmentName(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);

        ModelAndView mav = employeesController.getEmployeesByDepartmentName("dotNOT");
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testInsert(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);

        ModelAndView mav = employeesController.insertNewEmployee("Arkadiy", "Java", Date.valueOf("1993-05-11"), 900);
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testUpdate(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);

        ModelAndView mav = employeesController.updateEmployeeById(108L, "Aliaxander", "dotNOT",
                Date.valueOf("1992-03-05"), 1000);
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testDelete(){
        employeesController = new EmployeesController();
        employeesService = new WebAppEmployeesService();
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);

        ModelAndView mav = employeesController.deleteEmployeeById(108L);
        assertEquals("employees", mav.getViewName());
    }
}
