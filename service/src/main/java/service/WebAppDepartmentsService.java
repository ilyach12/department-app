package service;

import model.Department;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This a service class and he getting data from REST service by uri.
 * {@code DepartmentService} using RestTemplate class for the implementation
 * of data access.
 */
@Service
public class WebAppDepartmentsService implements IDepartmentsService {

    /**
     * Default url by REST service for getting data about departments.
     */
    private final String HOST_URL = "http://localhost:8080/server/departments";

    /**
     * {@code getAll} method getting all departments from REST.
     *
     * @return List of all employees
     */
    @Override
    public List<Department> getAll(){
        RestTemplate restTemplate = new RestTemplate();
        Department[] departments = restTemplate.getForObject(HOST_URL, Department[].class);
        return Arrays.asList(departments);
    }

    /**
     * Takes department name as parameter and getting founded department.
     *
     * @param departmentName name of founded department
     * @return List where contains all data about this department
     */
    @Override
    public List<Department> getDepartmentByNameWithEmployees(String departmentName){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "/{departmentName}";
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        Department[] department = restTemplate.getForObject(HOST_URL + uri, Department[].class, map);
        return Arrays.asList(department);
    }

    /**
     * {@code getAllDepartmentsWithEmployees} method getting all departments and list of all
     * employees who works in this department.
     *
     * @return List of all departments
     */
    @Override
    public List<Department> getAllDepartmentsWithEmployees(){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "/getAllDepartmentsWithEmployees";
        Department[] departments = restTemplate.getForObject(HOST_URL + uri, Department[].class);
        return Arrays.asList(departments);
    }

    /**
     * Insert new department in database.
     *
     * @param departmentName name of inserting department. Id generated is automatically
     */
    @Override
    public void insert(String departmentName){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "/insertNewRow/departmentName/{departmentName}";
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        restTemplate.postForLocation(HOST_URL + uri, Department.class, map);
    }

    /**
     * Updating information about department by ID of this department.
     *
     * @param id id of editable department
     * @param departmentName new name for department
     */
    @Override
    public void update(Long id, String departmentName){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "/rename/departmentWithId/{id}/newName/{departmentName}";
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("departmentName", departmentName);
        restTemplate.postForLocation(HOST_URL + uri, Department.class, map);
    }

    /**
     * Delete department by his name from database.
     *
     * @param departmentName name of department
     */
    @Override
    public void delete(String departmentName){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "/remove/department/{departmentName}";
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        restTemplate.postForLocation(HOST_URL + uri, Department.class, map);
    }
}
