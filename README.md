# Department app
Multi web application.
Client and rest-service for department-app.
For build and launch project you need installed Apache tomcat, Apache maven and Java 8.

## Ubuntu or Windows

For download source code from git use: 

```
git clone https://github.com/ilyach12/department-app.git
```
after this you need move into folder with source code and build war files. For that you just need to run build.bat(Windows) or build.sh(Ubuntu). 
Now you need launch this war files on Tomcat server and configure context path for client as "/", for server "/server". After that you can use application on next URL`s:

## For client:
### Departments

http://localhost:8080/departments - list of all departments

http://localhost:8080/departments/departmentsWithEmployees - list of all departments with list of all employees who works in this department

### Employees

http://localhost:8080/employees - list of all employees

## For server:
### Departments:

http://localhost:8080/server/departments - list of all departments

http://localhost:8080/server/departments/getAllDepartmentsWithEmployees - list of all departments with list of all employees who works in this department

http://localhost:8080/server/departments/addNewDepartment/departmentName/{department name} - inserting new department into DataBase

http://localhost:8080/server/departments/update/departmentWithId/{department id}/newName/{department name} - update name of department

http://localhost:8080/server/departments/remove/department/{department name} - remove department by name from DataBase

### Employees:

http://localhost:8080/server/employees - list of all employees

http://localhost:8080/server/employees/birthday/{birthday date} - get all employees who have date of bithday equals path variable {birthday date}

http://localhost:8080/server/employees/birthday/between/{first birthday date}/{second birthday date} - get all employees who have birthday date between first and second date

http://localhost:8080/server/employees/addNewEmployee/employeeName/{employeeName}/department/{department}/birthday/{birthday}/salary/{salary} - 
insert new employee into DataBase 

http://localhost:8080/server/employees/updateEmployeeData/employeeId/{id}/employeeName/{employeeName}/department/{department}/birthday/{birthday}/salary/{salary} -
update employee data by ID in DataBase 

http://localhost:8080/server/employees/remove/employee/{id} - remove employee by ID from DataBase