package controllerTest;

import controller.EmployeesController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import service.WebAppEmployeesService;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class EmployeesControllerTest {

    private EmployeesController employeesController = new EmployeesController();
    private RestTemplate restTemplate = new RestTemplate();
    private WebAppEmployeesService employeesService = new WebAppEmployeesService(restTemplate);
    private MockRestServiceServer restServiceServer;

    @Before
    public void setUp(){
        restServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetAll(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = employeesController.getAll();
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testGetEmployeesByBirthdayDate(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/birthday/1992-01-01"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        String birthday = "1992-01-01";
        ModelAndView mav = employeesController.getEmployeesByBirthdayDate(Date.valueOf(birthday));
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testGetEmployeesByBirthdayDateBetween(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/birthday/between/1992-01-01/1993-01-01"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        String birthday = "1992-01-01";
        String birthday1 = "1993-01-01";
        ModelAndView mav = employeesController.getEmployeesByBirthdayDateBetween(Date.valueOf(birthday),
                Date.valueOf(birthday1));
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testGetEmployeesByDepartmentName(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/department/QA"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = employeesController.getEmployeesByDepartmentName("QA");
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }

    /*@Test
    public void testInsert(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/addNewEmployee/employeeName/" +
                        "Jack/department/QA/birthday/1992-01-01/salary/1100"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = employeesController.insertNewEmployee("Jack", "QA", Date.valueOf("1992-01-01"), 1100);
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testUpdate(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/updatingEmployeeData/employeeId/1" +
                        "/employeeName/Jack/department/QA/birthday/1993-01-01/salary/1000"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = employeesController.updateEmployeeById(1L, "Jack", "QA", Date.valueOf("1993-01-01"), 1000);
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }

    @Test
    public void testDelete(){
        ReflectionTestUtils.setField(employeesController, "employeesService", employeesService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/remove/employee/1"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = employeesController.deleteEmployeeById(1L);
        restServiceServer.verify();
        assertEquals("employees", mav.getViewName());
    }*/
}
