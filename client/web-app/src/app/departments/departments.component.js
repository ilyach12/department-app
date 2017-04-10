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
var DepartmentsComponent = (function () {
    function DepartmentsComponent(appService) {
        var _this = this;
        this.appService = appService;
        this.appService.getDepartments().subscribe(function (departments) { _this.departments = departments; });
    }
    DepartmentsComponent.prototype.addDepartment = function (name) {
        this.appService.addDepartment(name);
        location.reload();
    };
    DepartmentsComponent.prototype.updateDepartment = function (id, name) {
        this.appService.updateDepartment(id, name);
        location.reload();
    };
    DepartmentsComponent.prototype.deleteDepartment = function (name) {
        this.appService.deleteDepartment(name);
        location.reload();
    };
    DepartmentsComponent = __decorate([
        core_1.Component({
            selector: 'departments',
            templateUrl: '../views/departments.html',
            providers: [app_service_1.AppService]
        }), 
        __metadata('design:paramtypes', [app_service_1.AppService])
    ], DepartmentsComponent);
    return DepartmentsComponent;
}());
exports.DepartmentsComponent = DepartmentsComponent;
//# sourceMappingURL=departments.component.js.map