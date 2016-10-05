package controllerTest;

import controller.DepartmentsController;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import service.WebAppDepartmentsService;

import static org.junit.Assert.assertEquals;

public class DepartmentsControllerTest {

    private DepartmentsController departmentsController;
    private WebAppDepartmentsService departmentsService;

    @Test
    public void testGetAll(){
        departmentsController = new DepartmentsController();
        departmentsService = new WebAppDepartmentsService();
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ModelAndView mav = departmentsController.getAll();
        assertEquals("departments", mav.getViewName());
    }

    @Test
    public void testGetDepartmentByName(){
        departmentsController = new DepartmentsController();
        departmentsService = new WebAppDepartmentsService();
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ModelAndView mav = departmentsController.getDepartmentByName("Java");
        assertEquals("departmentsWithEmployees", mav.getViewName());
    }

    @Test
    public void testGetAllDepartmentsWithEmployees(){
        departmentsController = new DepartmentsController();
        departmentsService = new WebAppDepartmentsService();
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ModelAndView mav = departmentsController.getAllDepartmentsWithEmployees();
        assertEquals("departmentsWithEmployees", mav.getViewName());
    }

    @Test
    public void testInsert(){
        departmentsController = new DepartmentsController();
        departmentsService = new WebAppDepartmentsService();
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ModelAndView mav = departmentsController.insertNewDepartment("Test");
        assertEquals("departments", mav.getViewName());
    }

    @Test
    public void testUpdate(){
        departmentsController = new DepartmentsController();
        departmentsService = new WebAppDepartmentsService();
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ModelAndView mav = departmentsController.updateDepartmentById(107L, "Testing");
        assertEquals("departments", mav.getViewName());
    }

    @Test
    public void testDelete(){
        departmentsController = new DepartmentsController();
        departmentsService = new WebAppDepartmentsService();
        ReflectionTestUtils.setField(departmentsController, "departmentsService", departmentsService);
        ModelAndView mav = departmentsController.deleteDepartmentByName("Testing");
        assertEquals("departments", mav.getViewName());
    }
}
