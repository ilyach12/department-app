"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var app_service_1 = require('../service/app.service');
var EmployeesComponent = (function () {
    function EmployeesComponent(appService) {
        this.appService = appService;
    }
    EmployeesComponent.prototype.ngOnInit = function () {
        this.getAllEmployees();
    };
    EmployeesComponent.prototype.getAllEmployees = function () {
        var _this = this;
        this.appService.getEmployees().subscribe(function (employees) { _this.employees = employees; });
    };
    EmployeesComponent.prototype.findByBirthdayDate = function (date) {
        var _this = this;
        this.appService.findByBirthdayDate(date).subscribe(function (employees) { _this.employees = employees; });
    };
    EmployeesComponent.prototype.findByBirthdayDateBetween = function (date1, date2) {
        var _this = this;
        this.appService.findByBirthdayDateBetween(date1, date2).subscribe(function (employees) { _this.employees = employees; });
    };
    EmployeesComponent.prototype.addEmployee = function (name, depId, bDay, salary) {
        this.appService.addEmployee(name, depId, bDay, salary);
        location.reload();
    };
    EmployeesComponent.prototype.updateEmployee = function (id, name, depId, bDay, salary) {
        this.appService.updateEmployee(id, name, depId, bDay, salary);
        location.reload();
    };
    EmployeesComponent.prototype.deleteEmployee = function (id) {
        this.appService.deleteEmployee(id);
        location.reload();
    };
    EmployeesComponent = __decorate([
        core_1.Component({
            selector: 'employees',
            templateUrl: '../views/employees.html',
            providers: [app_service_1.AppService]
        }), 
        __metadata('design:paramtypes', [app_service_1.AppService])
    ], EmployeesComponent);
    return EmployeesComponent;
}());
exports.EmployeesComponent = EmployeesComponent;
//# sourceMappingURL=employees.comonent.js.map