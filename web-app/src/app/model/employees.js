"use strict";
var Employees = (function () {
    function Employees(id, departmentName, fullName, birthday, salary, department_id) {
        this.id = id;
        this.departmentName = departmentName;
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
        this.department_id = department_id;
    }
    return Employees;
}());
exports.Employees = Employees;
//# sourceMappingURL=employees.js.map