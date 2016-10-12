package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import service.WebAppEmployeesService;

import java.sql.Date;

/**
 * Client controller. This class returned all needs data on the
 * JSP pages.
 */
@RestController
@RequestMapping("/employees")
@ComponentScan("service")
public class EmployeesController {

    @Autowired
    private WebAppEmployeesService employeesService;

    /**
     * This method getting list of all employees.
     *
     * @return employees.jsp with list of all employees
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll(){
        ModelAndView mav = new ModelAndView("employees");
        mav.addObject("employeesList", employeesService.getAll());
        return mav;
    }

    /**
     * {@code getEmployeesByBirthdayDate()} finds employees who has b-day date
     * equal date takes as parameter.
     *
     * @param birthday b-day date
     * @return employees.jsp with list of all founded employees
     */
    @RequestMapping(value = "/findEmployeesByBirthday", method = RequestMethod.GET)
    public ModelAndView getEmployeesByBirthdayDate(@RequestParam("birthday") Date birthday){
        ModelAndView mav = new ModelAndView("employees");
        mav.addObject("employeesList", employeesService.getEmployeesByBirthdayDate(birthday));
        return mav;
    }

    /**
     * Getting employees who has date of b-day between dates taking as
     * parameters,
     *
     * @param birthday first b-day date
     * @param birthday1 second b-day date
     * @return employees.jsp with list of all founded employees
     */
    @RequestMapping(value = "/findEmployeesByBirthdayBetween", method = RequestMethod.GET)
    public ModelAndView getEmployeesByBirthdayDateBetween(@RequestParam("birthday") Date birthday,
                                                          @RequestParam("birthday1") Date birthday1){
        ModelAndView mav = new ModelAndView("employees");
        mav.addObject("employeesList", employeesService.getEmployeesByBirthdayDateBetween(birthday, birthday1));
        return mav;
    }

    /**
     * Inset new employee from database.
     *
     * @return employees.jsp with list of all employees
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insertNewEmployee(@RequestParam("fullName") String fullName,
                                          @RequestParam("department_id") Long department_id,
                                          @RequestParam("birthday") Date birthday, @RequestParam("salary") int salary){
        employeesService.insert(fullName, department_id, birthday, salary);
        return getAll();
    }

    /**
     * Update information about employee by id.
     *
     * @return employees.jsp with list of all employees
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateEmployeeById(@RequestParam("id") Long id, @RequestParam("fullName") String employeeName,
                                           @RequestParam("department_id") Long department_id,
                                           @RequestParam("birthday") Date birthday, @RequestParam("salary") int salary){
        employeesService.update(id, employeeName, department_id, birthday, salary);
        return getAll();
    }

    /**
     * Delete employee from database by id.
     * @param id of employee who must be deleted
     * @return employees.jsp with list of all employees
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteEmployeeById(@RequestParam("id") Long id) {
        employeesService.delete(id);
        return getAll();
    }
}
