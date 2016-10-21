package controllerTest;

import controller.DepartmentsController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import service.WebAppDepartmentsService;

import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class DepartmentsControllerTest {

    private DepartmentsController departmentsController = new DepartmentsController();
    private RestTemplate restTemplate = new RestTemplate();
    private WebAppDepartmentsService departmentsService = new WebAppDepartmentsService(restTemplate);
    private MockRestServiceServer restServiceServer;

    @Before
    public void setUp(){
        restServiceServer = MockRestServiceServer.createServer(restTemplate);
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);

        ReflectionTestUtils.setField(departmentsService, "hostUrl", "http://localhost:8080/server/departments");
        ReflectionTestUtils.setField(departmentsService, "allWithEmployeesUri", "/getAllDepartmentsWithEmployees");
        ReflectionTestUtils.setField(departmentsService, "insertUri", "/addNewDepartment/departmentName/{departmentName}");
        ReflectionTestUtils.setField(departmentsService, "updateUri",
                "/update/departmentWithId/{id}/newName/{departmentName}");
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

        ModelAndView mav = departmentsController.getAll();
        restServiceServer.verify();
        assertViewName(mav, "departments");
        assertModelAttributeAvailable(mav, "departmentsList");
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/getAllDepartmentsWithEmployees"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.getAllDepartmentsWithEmployees();
        restServiceServer.verify();
        assertViewName(mav, "departmentsWithEmployees");
        assertModelAttributeAvailable(mav, "departmentsList");
    }

    @Test
    public void testInsert(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/addNewDepartment/departmentName/Test"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.insertNewDepartment("Test");
        restServiceServer.verify();
        assertViewName(mav, "departments");
        assertModelAttributeAvailable(mav, "departmentsList");
    }

    @Test
    public void testUpdate(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/update/departmentWithId/1/newName/Dep"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.updateDepartmentById(1L, "Dep");
        restServiceServer.verify();
        assertViewName(mav, "departments");
        assertModelAttributeAvailable(mav, "departmentsList");
    }

    @Test
    public void testDelete(){
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/remove/department/QA"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.deleteDepartmentByName("QA");
        restServiceServer.verify();
        assertViewName(mav, "departments");
        assertModelAttributeAvailable(mav, "departmentsList");
    }
}
