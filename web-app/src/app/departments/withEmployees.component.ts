import { Component }        from '@angular/core';
import { Router }           from '@angular/router';
import { AppService }       from '../service/app.service';
import { Departments }      from '../model/departments';

@Component({
  selector: 'departmentsWithEmployees',
  templateUrl: '../views/departmentsWithEmployees.html',
  providers: [AppService]
})
export class WithEmployeesComponent{
  departmentsWithEmployees: Departments[];

  constructor (private router: Router, private appService: AppService) {
    this.appService.getDepartmentsWithEmployees()
        .subscribe(departmentsWithEmployees=>
            { this.departmentsWithEmployees = departmentsWithEmployees });
  }
}
