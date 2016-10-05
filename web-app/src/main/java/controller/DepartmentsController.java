package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import service.WebAppDepartmentsService;

/**
 * Client controller. This class returned all needs data on the
 * JSP pages.
 */
@RestController
@RequestMapping("/departments")
@ComponentScan("service")
public class DepartmentsController {

    @Autowired
    private WebAppDepartmentsService departmentsService;

    /**
     * Getting all departments on departments.jsp
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll(){
        ModelAndView mav = new ModelAndView("departments");
        mav.addObject("departmentsList", departmentsService.getAll());
        return mav;
    }

    /**
     * Find and get all founded departments.
     *
     * @param departmentName name of searching department
     * @return list of founded departments on departmentsWithEmployees.jsp
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.GET)
    public ModelAndView getDepartmentByName(@RequestParam("departmentName") String departmentName){
        ModelAndView mav = new ModelAndView("departmentsWithEmployees");
        mav.addObject("departmentsList", departmentsService.getDepartmentByNameWithEmployees(departmentName));
        return mav;
    }

    /**
     * Getting all departments with list of employees who works in
     * this departments.
     *
     * @return list of all departments with employees from this departments
     */
    @RequestMapping(value = "/departmentsWithEmployees", method = RequestMethod.GET)
    public ModelAndView getAllDepartmentsWithEmployees(){
        ModelAndView mav = new ModelAndView("departmentsWithEmployees");
        mav.addObject("departmentsList", departmentsService.getAllDepartmentsWithEmployees());
        return mav;
    }

    /**
     * Inserting new department on database.
     *
     * @param departmentName name of new department
     * @return list of all departments on departments.jsp
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insertNewDepartment(@RequestParam("departmentName") String departmentName){
        departmentsService.insert(departmentName);
        return getAll();
    }

    /**
     * Edit information about department by id.
     *
     * @param id id of editable department
     * @param departmentName new name for this department
     * @return list of all departments on departments.jsp
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateDepartmentById(@RequestParam("id") Long id,
                                             @RequestParam("departmentName") String departmentName){
        departmentsService.update(id, departmentName);
        return getAll();
    }

    /**
     * Delete department by name.
     *
     * @param departmentName name of department
     * @return list of all departments on departments.jsp
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteDepartmentByName(@RequestParam("departmentName") String departmentName){
        departmentsService.delete(departmentName);
        return getAll();
    }
}
