import { Component }        from '@angular/core';
import { Router }           from '@angular/router';
import { AppService }       from '../service/app.service';
import { Departments }      from '../model/departments';

@Component({
  selector: 'departments',
  templateUrl: '../views/departments.html',
  providers: [AppService]
})
export class DepartmentsComponent{
  departments: Departments[];

  constructor (private router: Router, private appService: AppService) {
    this.appService.getDepartments()
        .subscribe(departments=>{ this.departments = departments });
  }
}
