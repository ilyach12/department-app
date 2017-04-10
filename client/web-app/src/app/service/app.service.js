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
var http_1 = require('@angular/http');
require('rxjs/add/operator/map');
require('rxjs/add/operator/toPromise');
var AppService = (function () {
    function AppService(http) {
        this.http = http;
        this.defaultDepartmentsUrl = 'http://127.0.0.1:8080/server/departments';
        this.defaultEmployeesUrl = 'http://127.0.0.1:8080/server/employees';
    }
    // Departments
    AppService.prototype.getDepartments = function () {
        return this.http.get(this.defaultDepartmentsUrl).map(function (res) { return res.json(); });
    };
    AppService.prototype.getDepartmentsWithEmployees = function () {
        return this.http.get(this.defaultDepartmentsUrl + '/employees').map(function (res) { return res.json(); });
    };
    AppService.prototype.addDepartment = function (name) {
        return this.http.post(this.defaultDepartmentsUrl + '/add/' + name, [])
            .toPromise();
    };
    AppService.prototype.deleteDepartment = function (name) {
        return this.http.post(this.defaultDepartmentsUrl + '/remove/' + name, [])
            .toPromise();
    };
    AppService.prototype.updateDepartment = function (id, name) {
        return this.http.post(this.defaultDepartmentsUrl + '/update/' + id + '/' + name, [])
            .toPromise();
    };
    // Employees
    AppService.prototype.getEmployees = function () {
        return this.http.get(this.defaultEmployeesUrl).map(function (res) { return res.json(); });
    };
    AppService.prototype.findByBirthdayDate = function (date) {
        return this.http.get(this.defaultEmployeesUrl + '/birthday/' + date).map(function (res) { return res.json(); });
    };
    AppService.prototype.findByBirthdayDateBetween = function (date1, date2) {
        return this.http.get(this.defaultEmployeesUrl + '/birthday/' + date1 + '/' + date2).map(function (res) { return res.json(); });
    };
    AppService.prototype.addEmployee = function (name, depId, bDay, salary) {
        return this.http.post(this.defaultEmployeesUrl + '/add/' + name + '/' + depId + '/' + bDay + '/' + salary, [])
            .toPromise();
    };
    AppService.prototype.updateEmployee = function (id, name, depId, bDay, salary) {
        return this.http.post(this.defaultEmployeesUrl + '/update/' + id + '/'
            + name + '/' + depId + '/' + bDay + '/' + salary, [])
            .toPromise();
    };
    AppService.prototype.deleteEmployee = function (id) {
        this.http.post(this.defaultEmployeesUrl + '/remove/' + id, [])
            .toPromise();
    };
    AppService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], AppService);
    return AppService;
}());
exports.AppService = AppService;
//# sourceMappingURL=app.service.js.map