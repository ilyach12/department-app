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
     * {@code getAllDepartments} getting all departments from database.
     *
     * @return List of all departments in Json format
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Department> getAllDepartments(){
        return departmentsService.getAll();
    }

    /**
     * {@code getAllDepartmentsWithEmployees} method getting list of all
     * departments with all employees who works in this department.
     *
     * @return List of all departments in Json format
     */
    @RequestMapping(value = "/getAllDepartmentsWithEmployees", method = RequestMethod.GET)
    public List<Department> getAllDepartmentsWithEmployees(){
        return departmentsService.getAllDepartmentsWithEmployees();
    }

    /**
     * This method finds one department with all employees from this
     * department.
     *
     * @param departmentName name of searching department
     * @return List which contains one department and list of all employees from this department
     */
    @RequestMapping(value = "/{departmentName}", method = RequestMethod.GET)
    public List<Department> getDepartmentByNameWithEmployees(@PathVariable("departmentName") String departmentName){
        return departmentsService.getDepartmentByNameWithEmployees(departmentName);
    }

    /**
     * Add new department on database. Id generate automatically.
     *
     * @param departmentName name of new department
     * @return List of all departments in Json format
     */
    @RequestMapping(value = "/insertNewRow/departmentName/{departmentName}", method = RequestMethod.POST)
    public List<Department> insertNewDepartment(@PathVariable("departmentName")
                                                                           String departmentName) {
        logger.info("Creating department: " + departmentName);
        departmentsService.insert(departmentName);
        logger.info("Department " + departmentName + " creating successfully");
        return departmentsService.getAll();
    }

    /**
     *  Edit information about department by Id of this department.
     *
     * @param id id of editable department
     * @param departmentName new name for this department
     * @return List of all departments in Json format
     */
    @RequestMapping(value = "/rename/departmentWithId/{id}/newName/{departmentName}",
            method = RequestMethod.POST)
    public List<Department> updateDepartmentNameById(@PathVariable("id") Long id,
                                                     @PathVariable("departmentName") String departmentName){
        logger.info("Updating department name with id: " + id);
        departmentsService.update(id, departmentName);
        logger.info("Update to " + departmentName + " successfully");
        return departmentsService.getAll();
    }

    /**
     * Delete department from database by department name.
     *
     * @param departmentName name of department
     * @return List of all departments in Json format
     */
    @RequestMapping(value = "/remove/department/{departmentName}", method = RequestMethod.POST)
    public List<Department> deleteDepartmentByName(@PathVariable("departmentName")
                                                                      String departmentName){
        logger.info("Removing " + departmentName + "...");
        departmentsService.delete(departmentName);
        logger.info("Row with name " + departmentName + " removed successfully");
        return departmentsService.getAll();
    }
}
