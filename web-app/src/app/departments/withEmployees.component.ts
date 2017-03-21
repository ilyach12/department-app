import { Component }        from '@angular/core';
import { AppService }       from '../service/app.service';
import { Departments }      from '../model/departments';

@Component({
  selector: 'departmentsWithEmployees',
  templateUrl: '../views/departmentsWithEmployees.html',
  providers: [AppService]
})
export class WithEmployeesComponent{
  departmentsWithEmployees: Departments[];

  constructor (private appService: AppService) {
    this.appService.getDepartmentsWithEmployees()
        .subscribe(departmentsWithEmployees=>{ this.departmentsWithEmployees = departmentsWithEmployees });
  }

  addDepartment(name: string){
    this.appService.addDepartment(name);
    location.reload();
  }

  updateDepartment(id: number, name: string){
    this.appService.updateDepartment(id, name);
    location.reload();
  }

  deleteDepartment(name: string){
    this.appService.deleteDepartment(name);
    location.reload();
  }
}
