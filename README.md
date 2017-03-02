# Department app
Multi web application.
Client and rest-service for department-app.
For build and launch project you need installed Apache tomcat, Apache maven, H2 database, Docker and Java 8.

## Dockerize

For download source code from git use: 

```
git clone https://github.com/ilyach12/department-app.git
```
1. Move into root folder and build war files, docker images and run docker containers. For that you just need to run build.bat(Windows) or build.sh(Ubuntu).
2. Go to http://localhost:81 in your web browser and configure H2 user with 'root' username and password.
3. Go to http://localhost:8081/web-app-1.0-SNAPSHOT/departments and use app.

# Application links
## For client:
### Departments

http://localhost:8081/web-app-1.0-SNAPSHOT/departments - list of all departments

http://localhost:8081/web-app-1.0-SNAPSHOT/departments/departmentsWithEmployees - list of all departments with list of all employees who works in this department

### Employees

http://localhost:8081/web-app-1.0-SNAPSHOT/employees - list of all employees

## For server:
### Departments:

http://localhost:8080/rest-1.0-SNAPSHOT/server/departments - list of all departments

http://localhost:8080/rest-1.0-SNAPSHOT/server/departments/getAllDepartmentsWithEmployees - list of all departments with list of all employees who works in this department

http://localhost:8080/rest-1.0-SNAPSHOT/server/departments/addNewDepartment/departmentName/{department name} - inserting new department into DataBase

http://localhost:8080/rest-1.0-SNAPSHOT/server/departments/update/departmentWithId/{department id}/newName/{department name} - update name of department

http://localhost:8080/rest-1.0-SNAPSHOT/server/departments/remove/department/{department name} - remove department by name from DataBase

### Employees:

http://localhost:8080/rest-1.0-SNAPSHOT/server/employees - list of all employees

http://localhost:8080/rest-1.0-SNAPSHOT/server/employees/birthday/{birthday date} - get all employees who have date of bithday equals path variable {birthday date}

http://localhost:8080/rest-1.0-SNAPSHOT/server/employees/birthday/between/{first birthday date}/{second birthday date} - get all employees who have birthday date between first and second date

http://localhost:8080/rest-1.0-SNAPSHOT/server/employees/addNewEmployee/employeeName/{employeeName}/department/{department}/birthday/{birthday}/salary/{salary} - 
insert new employee into DataBase 

http://localhost:8080/rest-1.0-SNAPSHOT/server/employees/updateEmployeeData/employeeId/{id}/employeeName/{employeeName}/department/{department}/birthday/{birthday}/salary/{salary} -
update employee data by ID in DataBase 

http://localhost:8080/rest-1.0-SNAPSHOT/server/employees/remove/employee/{id} - remove employee by ID from DataBase
