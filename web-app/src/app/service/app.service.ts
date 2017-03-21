import { Injectable }              from '@angular/core';
import { Http }          from '@angular/http';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class AppService {

  private defaultDepartmentsUrl: string = 'http://localhost:8080/server/departments';
  private defaultEmployeesUrl: string = 'http://localhost:8080/server/employees';

  constructor (private http: Http) {}

  // Departments
  getDepartments () {
    return this.http.get(this.defaultDepartmentsUrl).map(res=>res.json());
  }

  getDepartmentsWithEmployees () {
    return this.http.get(this.defaultDepartmentsUrl + '/getAllDepartmentsWithEmployees').map(res=>res.json());
  }

  addDepartment(name: string){
    return this.http.post(this.defaultDepartmentsUrl + '/addNewDepartment/departmentName/' + name, [])
      .toPromise();
  }

  deleteDepartment(name: string){
    return this.http.post(this.defaultDepartmentsUrl + '/remove/department/' + name, [])
      .toPromise();
  }

  updateDepartment(id: number, name: string){
    return this.http.post(this.defaultDepartmentsUrl + '/update/departmentId/' + id + '/newName/' + name, [])
      .toPromise();
  }

  // Employees
  getEmployees () {
    return this.http.get(this.defaultEmployeesUrl).map(res=>res.json());
  }

  findByBirthdayDate(date: string){
    return this.http.get(this.defaultEmployeesUrl + '/birthday/' + date).map(res=>res.json());
  }

  findByBirthdayDateBetween(date1: string, date2:string){
    return this.http.get(this.defaultEmployeesUrl + '/birthday/between/' + date1 + '/' + date2).map(res=>res.json());
  }

  addEmployee(name: string, depId: number, bDay: string, salary: number){
    return this.http.post(this.defaultEmployeesUrl + '/addNewEmployee/employeeName/' + name +
      '/departmentId/' + depId + '/birthday/' + bDay + '/salary/' + salary, [])
        .toPromise();
  }

  updateEmployee(id: number, name: string, depId: number, bDay: string, salary: number){
    return this.http.post(this.defaultEmployeesUrl + '/updateEmployeeData/employeeId/' + id + '/employeeName/' +
      name + '/departmentId/' + depId + '/birthday/' + bDay + '/salary/' + salary, [])
        .toPromise();
  }

  deleteEmployee(id: string){
    this.http.post(this.defaultEmployeesUrl + '/remove/employee/' + id, [])
        .toPromise();
  }
}
