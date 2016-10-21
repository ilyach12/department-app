package controller;

import model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.RestDepartmentsService;

import java.util.List;

/**
 * Rest service controller. This class returned all needs data under the
 * relevant mapping on Json format.
 * For use this API you just need use {@code RestTemplate} class on
 * Spring framework and contact for relevant mapping for gets all
 * needs data.
 */
@RestController
@RequestMapping("/departments")
@ComponentScan("service")
public class DepartmentsController {

    @Autowired
    private RestDepartmentsService departmentsService;

    private final Logger logger = LoggerFactory.getLogger(DepartmentsController.class);

    /**
     * {@code getAllDepartments()} gets all the departments from the database.
     *
     * @return List of all departments
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Department> getAllDepartments(){
        return departmentsService.getAll();
    }

    /**
     * {@code getAllDepartmentsWithEmployees()} gets list of all
     * departments with all employees who works in this department.
     *
     * @return List of all departments with employees list
     */
    @RequestMapping(value = "/getAllDepartmentsWithEmployees", method = RequestMethod.GET)
    public List<Department> getAllDepartmentsWithEmployees(){
        return departmentsService.getAllDepartmentsWithEmployees();
    }

    /**
     * Adds new department to the database.
     *
     * @param departmentName name of new department
     * @return List of all departments
     */
    @RequestMapping(value = "/addNewDepartment/departmentName/{departmentName}", method = RequestMethod.POST)
    public List<Department> insertNewDepartment(@PathVariable("departmentName") String departmentName) {
        logger.info("Creating department: " + departmentName);
        departmentsService.insert(departmentName);
        logger.info("Department '" + departmentName + "' created successfully");
        return departmentsService.getAll();
    }

    /**
     * Update information about the department by Id.
     *
     * @param id id of editable department
     * @param departmentName new name for this department
     * @return List of all departments
     */
    @RequestMapping(value = "/update/departmentId/{id}/newName/{departmentName}", method = RequestMethod.POST)
    public List<Department> updateDepartmentNameById(@PathVariable("id") Long id,
                                                     @PathVariable("departmentName") String departmentName){
        logger.info("Updating department name with id: " + id);
        departmentsService.update(id, departmentName);
        logger.info("Update to '" + departmentName + "' successfully complete");
        return departmentsService.getAll();
    }

    /**
     * Removes the department from the database by name.
     *
     * @param departmentName name of department
     * @return List of all departments
     */
    @RequestMapping(value = "/remove/department/{departmentName}", method = RequestMethod.POST)
    public List<Department> deleteDepartmentByName(@PathVariable("departmentName") String departmentName){
        logger.info("Removing '" + departmentName + "'...");
        departmentsService.delete(departmentName);
        logger.info("Row with name '" + departmentName + "' removed successfully");
        return departmentsService.getAll();
    }
}
