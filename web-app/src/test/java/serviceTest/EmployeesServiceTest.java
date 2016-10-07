package serviceTest;

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
        ReflectionTestUtils.setField(employeesService, "byDbayBetween", "/birthday/between/{birthday}/{birthday1}");
        ReflectionTestUtils.setField(employeesService, "byDepartmentName", "/department/{department}");
        ReflectionTestUtils.setField(employeesService, "insert",
                "/addNewEmployee/employeeName/{fullName}/department/{department}/birthday/{birthday}/salary/{salary}");
        ReflectionTestUtils.setField(employeesService, "update",
                "/updatingEmployeeData/employeeId/{id}/employeeName/{fullName}/department/{department}/birthday/{birthday}/salary/{salary}");
        ReflectionTestUtils.setField(employeesService, "delete", "/remove/employee/{id}");
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
    public void testGetEmployeesByDepartmentName(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/department/QA"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"department\":\"null\",\"fullName\":\"null\"," +
                                "\"birthday\":\"0000-00-00\",\"salary\":0}]",
                        MediaType.APPLICATION_JSON));

        employeesService.getEmployeesByDepartmentName("QA");
        restServiceServer.verify();
    }

    @Test
    public void testInsert(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/addNewEmployee/employeeName/" +
                        "Jack/department/QA/birthday/1992-01-01/salary/1100"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        employeesService.insert("Jack", "QA", Date.valueOf("1992-01-01"), 1100);
        restServiceServer.verify();
    }

    @Test
    public void testUpdate(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/employees/updatingEmployeeData/employeeId/1" +
                        "/employeeName/Jack/department/QA/birthday/1993-01-01/salary/1000"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        employeesService.update(1L, "Jack", "QA", Date.valueOf("1993-01-01"), 1000);
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
