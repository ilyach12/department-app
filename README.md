# Department app
Web application.
Rest-service on Java and client on AngularJS 2.
For build and launch project you need installed Apache tomcat, Apache maven, Java 8, H2 database and node.js.

##Download and run project:
<ul>
    <li>
        ```
        git clone https://github.com/ilyach12/department-app.git
        cd department-app
        bash build.sh
        ```
    </li>
    <li>Run H2 database</li>
    <li>Launch this war file on Tomcat server and configure context path as "/server"</li>
    <li>
        ```
        cd web-app
        bash run.sh
        ```
    </li>
    <li>Use application</li>
</ul>

## Application usage
### Departments

http://localhost:3000/departments - list of departments

http://localhost:3000/departmentsWithEmployees - list of departments with list of employees who works in this department

### Employees

http://localhost:3000/employees - list of employees