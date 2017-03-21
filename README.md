# Department app
Web application.
Rest-service on Java and client on AngularJS 2.
For build and launch project you need installed Apache tomcat, Apache maven, Java 8, H2 database and node.js.

## Download and run project:
### Download source code
```
git clone https://github.com/ilyach12/department-app.git
```

### Build rest
```
cd department-app
bash build.sh
```
        
<ul>
    <li>Run H2 database</li>
    <li>Launch <code>rest/target/rest-1.0-SNAPSHOT.war</code> file on Tomcat server and configure context path as "/server"</li>
</ul>

### Build and run client
```
cd web-app
bash run.sh
```

## Application usage
### Departments

<code>http://localhost:3000/departments</code> - list of departments

<code>http://localhost:3000/departmentsWithEmployees</code> - list of departments with list of employees who works in this department

### Employees

<code>http://localhost:3000/employees</code> - list of employees
