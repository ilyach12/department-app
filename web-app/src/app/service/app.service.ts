import { Injectable }              from '@angular/core';
import { Http, Response }          from '@angular/http';

import 'rxjs/add/operator/map';

@Injectable()
export class AppService {

  constructor (private http: Http) {}

  getDepartments () {
    return this.http.get(
      'http://localhost:8080/server/departments').map(res=>res.json());
  }

  getDepartmentsWithEmployees () {
    return this.http.get(
      'http://localhost:8080/server/departments/getAllDepartmentsWithEmployees')
        .map(res=>res.json())
  }

  getEmployees () {
    return this.http.get(
      'http://localhost:8080/server/employees').map(res=>res.json());
  }
}
