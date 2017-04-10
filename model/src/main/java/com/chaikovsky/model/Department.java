package com.chaikovsky.model;

import java.util.List;

/**
 * Domain class for Department data table
 */
public class Department {

    private Long id;
    private String departmentName;
    private List<Employees> employeesInThisDepartment;
    private int averageSalary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employees> getEmployeesInThisDepartment() {
        return employeesInThisDepartment;
    }

    public void setEmployeesInThisDepartment(List<Employees> employeesInThisDepartment) {
        this.employeesInThisDepartment = employeesInThisDepartment;
    }

    public int getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(int averageSalary) {
        this.averageSalary = averageSalary;
    }
}
