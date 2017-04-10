package com.chaikovsky.controller;

import com.chaikovsky.model.Employees;
import com.chaikovsky.service.EmployeesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Rest service controller. This class returned all needs data under the
 * relevant mapping on Json format.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employees")
@ComponentScan("service")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;
    private final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    /**
     * Gets all employees from the database.
     *
     * @return List of all employees
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Employees> getAllEmployees(){
        return employeesService.getAll();
    }

    /**
     * {@code getEmployeesByBirthdayDate()} Getting all employees who has b-day date
     * like date taken as parameter.
     *
     * @param birthday date of b-day employee
     * @return List of all founded employees
     */
    @RequestMapping(value = "/birthday/{birthday}", method = RequestMethod.GET)
    public List<Employees> getEmployeesByBirthdayDate(@PathVariable("birthday") Date birthday){
        return employeesService.getEmployeesByBirthdayDate(birthday);
    }

    /**
     * Find employees who have b-day between the dates taken as parameters.
     *
     * @param birthday first date of b-day
     * @param birthday1 second date of b-day
     * @return List of all founded employees
     */
    @RequestMapping(value = "/birthday/{birthday}/{birthday1}", method = RequestMethod.GET)
    public List<Employees> getEmployeesByBirthdayDateBetween(@PathVariable("birthday") Date birthday,
                                                             @PathVariable("birthday1") Date birthday1){
        return employeesService.getEmployeesByBirthdayDateBetween(birthday, birthday1);
    }

    /**
     * Insert new employee in database.
     *
     * @return List of all employees
     */
    @RequestMapping(value = "/add/{employeeName}/{department_id}/{birthday}/{salary}", method = RequestMethod.POST)
    public List<Employees> insertNewEmployee(@PathVariable("employeeName") String employeeName,
                                             @PathVariable("department_id") Long department_id, @PathVariable("birthday") Date birthday,
                                             @PathVariable("salary") int salary){
        logger.info("Inserting new data in Employees table...");
        employeesService.insert(employeeName, department_id, birthday, salary);
        logger.info(employeeName + department_id + birthday + salary + " insertNewDepartment successful");
        return employeesService.getAll();
    }

    /**
     * Update information about the employee by Id.
     *
     * @return List of all employees
     */
    @RequestMapping(value = "/update/{id}/{employeeName}/{department_id}/{birthday}/{salary}",
            method = RequestMethod.POST)
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
     * Removes the employee by name from the database.
     *
     * @param id id of employee who must be deleted
     * @return List of all employees
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public List<Employees> deleteEmployeeById(@PathVariable("id") Long id){
        employeesService.delete(id);
        logger.info("employee with " + id + " deleted successful");
        return employeesService.getAll();
    }
}
