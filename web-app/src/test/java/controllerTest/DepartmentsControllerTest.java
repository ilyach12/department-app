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

import static org.junit.Assert.assertEquals;
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
    }

    @Test
    public void testGetAll(){
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.getAll();
        restServiceServer.verify();
        assertEquals("departments", mav.getViewName());
    }

    @Test
    public void testGetDepartmentByName(){
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/QA"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.getDepartmentByName("QA");
        restServiceServer.verify();
        assertEquals("departmentsWithEmployees", mav.getViewName());
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/getAllDepartmentsWithEmployees"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "[{\"id\":0,\"departmentName\":\"null\",\"employeesInThisDepartment\":null,\"averageSalary\":0}]",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.getAllDepartmentsWithEmployees();
        restServiceServer.verify();
        assertEquals("departmentsWithEmployees", mav.getViewName());
    }

    /*@Test
    public void testInsert(){
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/insertNewRow/departmentName/Test"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0,\"}",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.insertNewDepartment("Test");
        restServiceServer.verify();
        assertEquals("departments", mav.getViewName());
    }

    @Test
    public void testUpdate(){
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/rename/departmentWithId/1/newName/Dep"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0,\"}",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.updateDepartmentById(1L, "Dep");
        restServiceServer.verify();
        assertEquals("departments", mav.getViewName());
    }

    @Test
    public void testDelete(){
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        restServiceServer
                .expect(requestTo("http://localhost:8080/server/departments/remove/department/QA"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"id\" : \"0\"}",
                        MediaType.APPLICATION_JSON));

        ModelAndView mav = departmentsController.deleteDepartmentByName("QA");
        restServiceServer.verify();
        assertEquals("departments", mav.getViewName());
    }*/
}
