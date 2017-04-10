"use strict";
var Departments = (function () {
    function Departments(id, departmentName, employeesInThisDepartment, averageSalary) {
        this.id = id;
        this.departmentName = departmentName;
        this.employeesInThisDepartment = employeesInThisDepartment;
        this.averageSalary = averageSalary;
    }
    return Departments;
}());
exports.Departments = Departments;
//# sourceMappingURL=departments.js.map