package controller;

import model.Employees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.RestEmployeesService;

import java.sql.Date;
import java.util.List;

/**
 * Rest service controller. This class returned all needs data under the
 * relevant mapping on Json format.
 * For use this API you just need use {@code RestTemplate} class on
 * Spring framework and contact for relevant mapping for gets all
 * needs data.
 */
@RestController
@RequestMapping("/employees")
@ComponentScan("service")
public class EmployeesController {

    @Autowired
    private RestEmployeesService employeesService;
    private final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    /**
     * Getting all employees from database.
     *
     * @return List of all employees in Json format
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Employees> getAllEmployees(){
        return employeesService.getAll();
    }

    /**
     * {@code getEmployeesByBirthdayDate()} Getting all employees who has b-day date
     * like date taking as parameter.
     *
     * @param birthday date of b-day employee
     * @return List of all founded employees in Json format
     */
    @RequestMapping(value = "/birthday/{birthday}", method = RequestMethod.GET)
    public List<Employees> getEmployeesByBirthdayDate(@PathVariable("birthday") Date birthday){
        return employeesService.getEmployeesByBirthdayDate(birthday);
    }

    /**
     * Found employees who has b-day between dates taking as parameters.
     *
     * @param birthday first date of b-day
     * @param birthday1 second date of b-day
     * @return List of all founded employees in Json format
     */
    @RequestMapping(value = "/birthday/between/{birthday}/{birthday1}", method = RequestMethod.GET)
    public List<Employees> getEmployeesByBirthdayDateBetween(@PathVariable("birthday") Date birthday,
                                                             @PathVariable("birthday1") Date birthday1){
        return employeesService.getEmployeesByBirthdayDateBetween(birthday, birthday1);
    }

    /**
     * Insert new employee in database. Id generate automatically.
     *
     * @return List of all employees in Json format
     */
    @RequestMapping(value = "/addNewEmployee/employeeName/{employeeName}/departmentId/{department_id}/" +
            "birthday/{birthday}/salary/{salary}", method = RequestMethod.POST)
    public List<Employees> insertNewEmployee(@PathVariable("employeeName") String employeeName,
                           @PathVariable("department_id") Long department_id, @PathVariable("birthday") Date birthday,
                           @PathVariable("salary") int salary){
        logger.info("Inserting new data in Employees table...");
        employeesService.insert(employeeName, department_id, birthday, salary);
        logger.info(employeeName + department_id + birthday + salary + " insertNewDepartment successful");
        return employeesService.getAll();
    }

    /**
     * Update information about employee by id of this employee.
     *
     * @return List of all employees in Json format
     */
    @RequestMapping(value = "/updateEmployeeData/employeeId/{id}/employeeName/{employeeName}/" +
            "departmentId/{department_id}/birthday/{birthday}/salary/{salary}", method = RequestMethod.POST)
    public List<Employees> updateEmployeeDataById(@PathVariable("id") Long id,
                                                  @PathVariable("employeeName") String employeeName,
                                                  @PathVariable("department_id") Long department_id,
                                                  @PathVariable("birthday") Date birthday,
                                                  @PathVariable("salary") int salary){
        employeesService.update(id, employeeName, department_id, birthday, salary);
        logger.info(employeeName + " updated successful");
        return employeesService.getAll();
    }

    /**
     * Remove employee by name from database.
     *
     * @param id id of employee who must be deleted
     * @return List of all employees in Json format
     */
    @RequestMapping(value = "/remove/employee/{id}", method = RequestMethod.POST)
    public List<Employees> deleteEmployeeById(@PathVariable("id") Long id){
        employeesService.delete(id);
        logger.info("employee with " + id + " deleted successful");
        return employeesService.getAll();
    }
}
