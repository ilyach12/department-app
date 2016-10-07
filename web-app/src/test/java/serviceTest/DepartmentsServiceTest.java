package serviceTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import service.WebAppDepartmentsService;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class DepartmentsServiceTest {

    private RestTemplate restTemplate = new RestTemplate();
    private WebAppDepartmentsService departmentsService = new WebAppDepartmentsService(restTemplate);
    private MockRestServiceServer restServiceServer;

    @Before
    public void setUp(){
        restServiceServer = MockRestServiceServer.createServer(restTemplate);

        ReflectionTestUtils.setField(departmentsService, "hostUrl", "http://localhost:8080/server/departments");
        ReflectionTestUtils.setField(departmentsService, "byNameUri", "/{departmentName}");
        ReflectionTestUtils.setField(departmentsService, "allWithEmployeesUri", "/getAllDepartmentsWithEmployees");
        ReflectionTestUtils.setField(departmentsService, "insertUri", "/insertNewRow/departmentName/{departmentName}");
        ReflectionTestUtils.setField(departmentsService, "updateUri",
                "/rename/departmentWithId/{id}/newName/{departmentName}");
        ReflectionTestUtils.setField(departmentsService, "deleteUri", "/remove/department/{departmentName}");
    }

    @Test
    public void testGetAll(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        departmentsService.getAll();
        restServiceServer.verify();
    }

    @Test
    public void testGetDepartmentByNameWithEmployees(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/QA"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        departmentsService.getDepartmentByNameWithEmployees("QA");
        restServiceServer.verify();
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/getAllDepartmentsWithEmployees"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        departmentsService.getAllDepartmentsWithEmployees();
        restServiceServer.verify();
    }

    @Test
    public void testInsert(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/insertNewRow/departmentName/Test"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0,\"}",
                        MediaType.APPLICATION_JSON));

        departmentsService.insert("Test");
        restServiceServer.verify();
    }

    @Test
    public void testUpdate(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/rename/departmentWithId/1/newName/Dep"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0,\"}",
                        MediaType.APPLICATION_JSON));
        departmentsService.update(1L, "Dep");
        restServiceServer.verify();
    }

    @Test
    public void testDelete(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/remove/department/QA"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        departmentsService.delete("QA");
        restServiceServer.verify();
    }
}
