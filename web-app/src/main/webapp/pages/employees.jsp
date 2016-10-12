<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/materialize.min.css"/>" type="text/css" rel="stylesheet" media="screen,projection">
    <title>Employees</title>
</head>
<body>
<div class="container">
    <div class="row card">
        <div class="col s5">
            <h5>Search by birthday: </h5>
            <form action="/employees/findEmployeesByBirthday" method="get">
                <input name="birthday" type="date" required>
                <button class="btn waves-effect waves-light" type="submit">Search</button>
            </form>
        </div>
        <div class="col s5 offset-s1">
            <h5>Search between birthday dates: </h5>
            <form action="/employees/findEmployeesByBirthdayBetween" method="get">
                <input name="birthday" type="date" required>
                <input name="birthday1" type="date" required>
                <button class="btn waves-effect waves-light" type="submit">Search</button>
            </form>
        </div>
    </div>
    <div class="row card">
        <div class="col s12">
            <table class="striped">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Full name</th>
                    <th>Department</th>
                    <th>Birthday</th>
                    <th>Salary</th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="employeesList" scope="request" type="java.util.List"/>
                <c:forEach items="${employeesList}" var="item">
                    <tr>
                        <th>${item.id}</th>
                        <th>${item.fullName}</th>
                        <th>${item.departmentName}</th>
                        <th>${item.birthday}</th>
                        <th>${item.salary}$</th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <hr>
        <div class="center">
            <p><a class="waves-effect waves-light btn" href="/departments/departmentsWithEmployees">
                Departments with employees</a>
            <a class="waves-effect waves-light btn" href="/departments">Departments</a>
            <a class="waves-effect waves-light btn" href="/employees">Show all</a></p>
        </div>
    </div>
    <div class="row card">
        <div class="col s5">
            <h5>Add new employee</h5>
            <form action="/employees/insert" method="post">
                <input placeholder="employee name" name="fullName" type="text" required>
                <input placeholder="department id" name="department_id" type="text" pattern="^[ 0-9]+$" required>
                <input placeholder="birthday date" name="birthday" type="date" required>
                <input placeholder="salary" name="salary" type="text" required>
                <button class="btn waves-effect waves-light" type="submit">Create</button>
            </form>
        </div>
        <div class="col s5 offset-s1">
            <h5>Update</h5>
            <form action="/employees/update" method="post">
                <input placeholder="id of editable employee" name="id" type="text" pattern="^[ 0-9]+$" required>
                <input placeholder="employee name" name="fullName" type="text" required>
                <input placeholder="department id" name="department_id" type="text" pattern="^[ 0-9]+$" required>
                <input placeholder="birthday date" name="birthday" type="date" required>
                <input placeholder="salary" name="salary" type="text" required>
                <button class="btn waves-effect waves-light" type="submit">Update</button>
            </form>
        </div>
        <div class="col s5">
            <h5>Delete employee</h5>
            <form action="/employees/delete" method="post">
                <input placeholder="employee id" name="id" type="text" pattern="^[ 0-9]+$" required>
                <button class="btn waves-effect waves-light" type="submit">Delete</button>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/materialize.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jq.js"/>" type="text/javascript"></script>
</body>
</html>
