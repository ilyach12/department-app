# Department app
Web application.
Client and rest-service for department-app.
For build and launch project you need installed Apache tomcat, Apache maven, Java 8, H2 database and node.js.

## Ubuntu or Windows

For download source code from git use: 

```
git clone https://github.com/ilyach12/department-app.git
```
after that you need move into folder with source code and build war file. For that you just need to run build.sh(Ubuntu).
Now you need launch this war file on Tomcat and configure context path as "/server".
Next goto /web-app and run build.sh(Ubuntu).

After that you can use application on next URL`s:

### Departments

http://localhost:3000/departments - list of all departments

http://localhost:3000/departmentsWithEmployees - list of all departments with list of all employees who works in this department

### Employees

http://localhost:3000/employees - list of all employees