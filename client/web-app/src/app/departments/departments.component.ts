import { Component }        from '@angular/core';
import { AppService }       from '../service/app.service';
import { Departments }      from '../model/departments';

@Component({
  selector: 'departments',
  templateUrl: '../views/departments.html',
  providers: [AppService]
})
export class DepartmentsComponent{
  departments: Departments[];

  constructor (private appService: AppService) {
    this.appService.getDepartments().subscribe(departments=>{ this.departments = departments });
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
