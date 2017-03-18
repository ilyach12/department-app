import { Component }        from '@angular/core';
import { Router }           from '@angular/router';
import { AppService }       from '../service/app.service';
import { Employees }        from '../model/employees';

@Component({
  selector: 'employees',
  templateUrl: '../views/employees.html',
  providers: [AppService]
})
export class EmployeesComponent{
  employees: Employees[];

  constructor (private appService: AppService) {
    this.appService.getEmployees()
        .subscribe(employees=>{ this.employees = employees });
  }
}
