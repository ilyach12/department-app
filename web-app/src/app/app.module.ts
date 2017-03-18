import { NgModule }                 from '@angular/core';
import { BrowserModule }            from '@angular/platform-browser';
import { FormsModule }              from '@angular/forms';
import { RouterModule, Routes }     from '@angular/router';
import { HttpModule, JsonpModule }  from '@angular/http';

import { AppComponent }             from './app.component';
import { DepartmentsComponent }     from './departments/departments.component'
import { WithEmployeesComponent }   from './departments/withEmployees.component'
import { EmployeesComponent }       from './employees/employees.comonent'

const appRoutes: Routes = [
  { path: 'departments', component: DepartmentsComponent },
  { path: 'employees', component: EmployeesComponent },
  { path: 'departmentsWithEmployees', component: WithEmployeesComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule
  ],
  declarations: [
    AppComponent,
    DepartmentsComponent,
    EmployeesComponent,
    WithEmployeesComponent
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule {}
