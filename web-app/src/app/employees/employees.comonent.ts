import { Component, OnInit }        from '@angular/core';
import { AppService }       from '../service/app.service';
import { Employees }        from '../model/employees';

@Component({
  selector: 'employees',
  templateUrl: '../views/employees.html',
  providers: [AppService]
})
export class EmployeesComponent implements OnInit{
  employees: Employees[];

  constructor (private appService: AppService) {}

  ngOnInit(){
    this.getAllEmployees();
  }

  getAllEmployees(){
    this.appService.getEmployees().subscribe(employees=>{ this.employees = employees });
  }

  findByBirthdayDate(date: string){
    this.appService.findByBirthdayDate(date).subscribe(employees=>{ this.employees = employees });
  }

  findByBirthdayDateBetween(date1: string, date2: string){
    this.appService.findByBirthdayDateBetween(date1, date2).subscribe(employees=>{ this.employees = employees });
  }

  addEmployee(name: string, depId: number, bDay: string, salary: number){
    this.appService.addEmployee(name, depId, bDay, salary);
    location.reload();
  }
  
  updateEmployee(id: number, name: string, depId: number, bDay: string, salary: number){
    this.appService.updateEmployee(id, name, depId, bDay, salary);
    location.reload();
  }
  
  deleteEmployee(id: string){
    this.appService.deleteEmployee(id);
    location.reload();
  }
}
