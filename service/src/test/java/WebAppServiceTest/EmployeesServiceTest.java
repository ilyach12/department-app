package WebAppServiceTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import service.WebAppEmployeesService;

import java.sql.Date;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class EmployeesServiceTest {

    private RestTemplate restTemplate = new RestTemplate();
    private WebAppEmployeesService employeesService = new WebAppEmployeesService(restTemplate);
    private MockRestServiceServer restServiceServer;

    @Before
    public void setUp(){
        restServiceServer = MockRestServiceServer.createServer(restTemplate);

        ReflectionTestUtils.setField(employeesService, "hostUrl", "http://localhost:8080/server/employees");
        ReflectionTestUtils.setField(employeesService, "byBdayUri", "/birthday/{birthday}");
        ReflectionTestUtils.setField(employeesService, "byBdayBetweenUri", "/birthday/between/{birthday}/{birthday1}");
        ReflectionTestUtils.setField(employeesService, "insertUri",
                "/addNewEmployee/employeeName/{fullName}/departmentId/{department_id}/birthday/{birthday}/salary/{salary}");
        ReflectionTestUtils.setField(employeesService, "updateUri",
                "/updateEmployeeData/employeeId/{id}/employeeName/{fullName}/departmentId/{department_id}" +
                        "/birthday/{birthday}/salary/{salary}");
        ReflectionTestUtils.setField(employeesService, "deleteUri", "/remove/employee/{id}");
    }

    @Test
    public void testGetAll(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        employeesService.getAll();
        restServiceServer.verify();
    }

    @Test
    public void testGetEmployeesByBirthdayDate(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/birthday/1992-01-01"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        employeesService.getEmployeesByBirthdayDate(Date.valueOf("1992-01-01"));
        restServiceServer.verify();
    }

    @Test
    public void testGetEmployeesByBirthdayDateBetween(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/birthday/between/1992-01-01/1993-01-01"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        employeesService.getEmployeesByBirthdayDateBetween(Date.valueOf("1992-01-01"), Date.valueOf("1993-01-01"));
        restServiceServer.verify();
    }

    @Test
    public void testInsert(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/addNewEmployee/employeeName/" +
                        "Jack/departmentId/1/birthday/1992-01-01/salary/1100"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        employeesService.insert("Jack", 1L, Date.valueOf("1992-01-01"), 1100);
        restServiceServer.verify();
    }

    @Test
    public void testUpdate(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/updateEmployeeData/employeeId/1" +
                        "/employeeName/Jack/departmentId/1/birthday/1993-01-01/salary/1000"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        employeesService.update(1L, "Jack", 1L, Date.valueOf("1993-01-01"), 1000);
        restServiceServer.verify();
    }

    @Test
    public void testDelete(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/remove/employee/1"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        employeesService.delete(1L);
        restServiceServer.verify();
    }
}
