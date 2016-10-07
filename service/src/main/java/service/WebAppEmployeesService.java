package service;

import model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This a service class and he getting data from REST service by uri.
 * {@code EmployeeService} using RestTemplate class for the implementation
 * of data access.
 */
@Service
@PropertySource("classpath:uri.properties")
public class WebAppEmployeesService implements IEmployeesService {

    private final RestTemplate restTemplate;
    /**
     * Rest service url for managing employees data.
     */
    @Value("${employeesHost}")
    private String hostUrl;
    @Value("${employeesByBday}")
    private String byBdayUri;
    @Value("${employeesByBdayBetween}")
    private String byDbayBetween;
    @Value("${employeesByDeaprtmentName}")
    private String byDepartmentName;
    @Value("${insertEmployee}")
    private String insert;
    @Value("${updateEmployee}")
    private String update;
    @Value("${deleteEmployee}")
    private String delete;

    @Autowired
    public WebAppEmployeesService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     * {@code getAll} method getting all employees from REST.
     *
     * @return List of all employees
     */
    @Override
    public List<Employees> getAll(){
        Employees[] employees = restTemplate.getForObject(hostUrl, Employees[].class);
        return Arrays.asList(employees);
    }

    /**
     * Find all employees who has a certain by taking parameter
     * date of b-day.
     *
     * @param birthday takes date f birthday
     * @return List of all found employees with this b-day date
     */
    @Override
    public List<Employees> getEmployeesByBirthdayDate(Date birthday) {
        Map<String, Date> map = new HashMap<>();
        map.put("birthday", birthday);

        Employees[] employees = restTemplate.getForObject(hostUrl + byBdayUri, Employees[].class, map);
        return Arrays.asList(employees);
    }

    /**
     * Find all employees who has a b-da date between taking parameters
     * date of b-days.
     *
     * @param birthday first b-day date
     * @param birthday1 second b-day date
     * @return List of all fond employees who has b-day date between taking parameters
     */
    @Override
    public List<Employees> getEmployeesByBirthdayDateBetween(Date birthday, Date birthday1) {
        Map<String, Date> map = new HashMap<>(2);
        map.put("birthday", birthday);
        map.put("birthday1", birthday1);

        Employees[] employees = restTemplate.getForObject(hostUrl + byDbayBetween, Employees[].class, map);
        return Arrays.asList(employees);
    }

    /**
     * Getting all employees by department where employees work.
     *
     * @param departmentName name of searching department
     * @return List of employees who works in founded department
     */
    @Override
    public List<Employees> getEmployeesByDepartmentName(String departmentName) {
        Map<String, String> map = new HashMap<>();
        map.put("department", departmentName);

        Employees[] employees = restTemplate.getForObject(hostUrl + byDepartmentName, Employees[].class, map);
        return Arrays.asList(employees);
    }

    /**
     * {@code insert} inserting new employee in data table.
     */
    @Override
    public void insert(String fullName, String department, Date birthday, int salary) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("fullName", fullName);
        map.put("department", department);
        map.put("birthday", birthday);
        map.put("salary", salary);
        restTemplate.postForLocation(hostUrl + insert, Employees.class, map);
    }

    /**
     * Update employee data in table by ID of this employee.
     */
    @Override
    public void update(Long id, String fullName, String department, Date birthday, int salary) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("id", id);
        map.put("fullName", fullName);
        map.put("department", department);
        map.put("birthday", birthday);
        map.put("salary", salary);
        restTemplate.postForLocation(hostUrl + update, Employees.class, map);
    }

    /**
     * Delete employee from database by name of this employee.
     *
     * @param id of employee who must be deleted
     */
    @Override
    public void delete(Long id) {
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        restTemplate.postForLocation(hostUrl + delete, Employees.class, map);
    }

}
