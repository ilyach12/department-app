import { Injectable }              from '@angular/core';
import { Http }          from '@angular/http';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class AppService {

  private defaultDepartmentsUrl: string = 'http://127.0.0.1:8080/server/departments';
  private defaultEmployeesUrl: string = 'http://127.0.0.1:8080/server/employees';

  constructor (private http: Http) {}

  // Departments
  getDepartments () {
    return this.http.get(this.defaultDepartmentsUrl).map(res=>res.json());
  }

  getDepartmentsWithEmployees () {
    return this.http.get(this.defaultDepartmentsUrl + '/employees').map(res=>res.json());
  }

  addDepartment(name: string){
    return this.http.post(this.defaultDepartmentsUrl + '/add/' + name, [])
      .toPromise();
  }

  deleteDepartment(name: string){
    return this.http.post(this.defaultDepartmentsUrl + '/remove/' + name, [])
      .toPromise();
  }

  updateDepartment(id: number, name: string){
    return this.http.post(this.defaultDepartmentsUrl + '/update/' + id + '/' + name, [])
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
    return this.http.get(this.defaultEmployeesUrl + '/birthday/' + date1 + '/' + date2).map(res=>res.json());
  }

  addEmployee(name: string, depId: number, bDay: string, salary: number){
    return this.http.post(this.defaultEmployeesUrl + '/add/' + name + '/' + depId + '/' + bDay + '/' + salary, [])
        .toPromise();
  }

  updateEmployee(id: number, name: string, depId: number, bDay: string, salary: number){
    return this.http.post(this.defaultEmployeesUrl + '/update/' + id + '/'
      + name + '/' + depId + '/' + bDay + '/' + salary, [])
        .toPromise();
  }

  deleteEmployee(id: string){
    this.http.post(this.defaultEmployeesUrl + '/remove/' + id, [])
        .toPromise();
  }
}
