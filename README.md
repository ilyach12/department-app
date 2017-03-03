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

http://localhost:8080/sign - sign in page. Default username: root, password: root

### Departments

http://localhost:8080/departments - list of all departments

http://localhost:8080/departments/departmentsWithEmployees - list of all departments with list of all employees who works in this department

### Employees

http://localhost:8080/employees - list of all employees