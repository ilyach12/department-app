import { Employees }       from './employees';

export class Departments {
  constructor(
    public id: number,
    public departmentName: string,
    public employeesInThisDepartment: Employees[],
    public averageSalary: string) { }
}
