package service;

import model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:uri.properties")
public class WebAppDepartmentsService implements IDepartmentsService {

    private final RestTemplate restTemplate;
    /**
     * Rest service url for managing departments data.
     */
    @Value("${departmentsHost}")
    private String hostUrl;
    @Value("${allDepartmentsWithEmployees}")
    private String allWithEmployeesUri;
    @Value("${insertDepartment}")
    private String insertUri;
    @Value("${updateDepartment}")
    private String updateUri;
    @Value("${deleteDepartment}")
    private String deleteUri;

    @Autowired
    public WebAppDepartmentsService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     * {@code getAll} method getting all departments from REST.
     *
     * @return List of all employees
     */
    @Override
    public List<Department> getAll(){
        Department[] departments = restTemplate.getForObject(hostUrl, Department[].class);
        return Arrays.asList(departments);
    }

    /**
     * {@code getAllDepartmentsWithEmployees} method getting all departments and list of all
     * employees who works in this department.
     *
     * @return List of all departments
     */
    @Override
    public List<Department> getAllDepartmentsWithEmployees(){
        Department[] departments = restTemplate.getForObject(hostUrl + allWithEmployeesUri, Department[].class);
        return Arrays.asList(departments);
    }

    /**
     * Insert new department in database.
     *
     * @param departmentName name of inserting department. Id generated is automatically
     */
    @Override
    public void insert(String departmentName){
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        restTemplate.postForLocation(hostUrl + insertUri, Department.class, map);
    }

    /**
     * Updating information about department by ID of this department.
     *
     * @param id id of editable department
     * @param departmentName new name for department
     */
    @Override
    public void update(Long id, String departmentName){
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("departmentName", departmentName);
        restTemplate.postForLocation(hostUrl + updateUri, Department.class, map);
    }

    /**
     * Delete department by his name from database.
     *
     * @param departmentName name of department
     */
    @Override
    public void delete(String departmentName){
        Map<String, String> map = new HashMap<>();
        map.put("departmentName", departmentName);
        restTemplate.postForLocation(hostUrl + deleteUri, Department.class, map);
    }
}
