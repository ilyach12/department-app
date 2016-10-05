package serviceTest;

import model.Department;
import org.junit.Before;
import org.junit.Test;
import service.WebAppDepartmentsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DepartmentsServiceTest {

    private WebAppDepartmentsService departmentService;
    private List<Department> list = new ArrayList<>();

    @Before
    public void initDepartment(){
        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("Java");
        department.setEmployeesInThisDepartment(new ArrayList<>());
        department.setAverageSalary(800);

        Department department1 = new Department();
        department1.setId(2L);
        department1.setDepartmentName(".NET");
        department1.setEmployeesInThisDepartment(new ArrayList<>());
        department1.setAverageSalary(800);

        list.add(department);
        list.add(department1);
    }

    @Test
    public void testGetAll(){
        departmentService = mock(WebAppDepartmentsService.class);
        when(departmentService.getAll()).thenReturn(list);
        assertEquals(2, departmentService.getAll().size());
    }

    @Test
    public void testGetAllWithEmployees(){
        departmentService = mock(WebAppDepartmentsService.class);
        when(departmentService.getAllDepartmentsWithEmployees()).thenReturn(list);
        assertEquals(2, departmentService.getAllDepartmentsWithEmployees().size());
    }

    @Test
    public void testGetDepartmentByNameWithEmployees(){
        departmentService = new WebAppDepartmentsService();
        List<Department> department = departmentService.getDepartmentByNameWithEmployees("Java");
        assertEquals(1, department.size());
    }

    @Test
    public void testInsert(){
        departmentService = new WebAppDepartmentsService();
        departmentService.insert("Test");
        List<Department> departments = departmentService.getAll();
        assertEquals(6, departments.size());
        List<Department> department = departmentService.getDepartmentByNameWithEmployees("Test");
        assertEquals(1, department.size());

    }

    @Test
    public void testUpdate(){
        departmentService = new WebAppDepartmentsService();
        departmentService.update(107L, "Testing");
        List<Department> departments = departmentService.getAll();
        assertEquals(6, departments.size());
        List<Department> department = departmentService.getDepartmentByNameWithEmployees("Testing");
        assertEquals(1, department.size());
    }

    @Test
    public void testDelete(){
        departmentService = new WebAppDepartmentsService();
        departmentService.delete("Testing");
        List<Department> departments = departmentService.getAll();
        assertEquals(5, departments.size());
    }
}
