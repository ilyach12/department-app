<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/materialize.min.css"/>" type="text/css" rel="stylesheet" media="screen,projection">
    <title>Departments</title>
</head>
<body>
<div class="container">
    <div class="row card">
        <div class="col s4">
            <h5>Search by department name: </h5>
        </div>
        <div class="col s4">
            <form action="/departments/searchByName" method="get">
                <input placeholder="department name" name="departmentName" type="search" required>
                <button class="btn waves-effect waves-light" type="submit">Search</button>
            </form>
        </div>
    </div>
    <div class="row card">
        <div class="col s12">
            <h4>Departments</h4>
            <table class="striped">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Department</th>
                        <th>Average salary</th>
                    </tr>
                </thead>
                <tbody>
                <jsp:useBean id="departmentsList" scope="request" type="java.util.List"/>
                <c:forEach items="${departmentsList}" var="item">
                    <tr>
                        <th>${item.id}</th>
                        <th>${item.departmentName}</th>
                        <th>${item.averageSalary}$</th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <hr>
        <div class="center">
            <p><a class="waves-effect waves-light btn" href="/departments/departmentsWithEmployees">With employees</a>
            <a class="waves-effect waves-light btn" href="/employees">Employees</a>
            <a class="waves-effect waves-light btn" href="/departments">Show all</a></p>
        </div>
    </div>
    <div class="row card">
        <div class="col s5">
            <h5>Create new department</h5>
            <form action="/departments/insert" method="post">
                <input placeholder="department name" name="departmentName" type="text" required>
                <button class="btn waves-effect waves-light" type="submit">Create</button>
            </form>
        </div>
        <div class="col s5 offset-s1">
            <h5>Update</h5>
            <form action="/departments/update" method="post">
                <input placeholder="id of editable department" name="id" type="text" pattern="^[ 0-9]+$" required>
                <input placeholder="new department name" name="departmentName" type="text" required>
                <button class="btn waves-effect waves-light" type="submit">Update</button>
            </form>
        </div>
        <div class="col s5">
            <h5>Delete department</h5>
            <form action="/departments/delete" method="post">
                <input placeholder="department name" name="departmentName" type="text" required>
                <button class="btn waves-effect waves-light" type="submit">Delete</button>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/materialize.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jq.js"/>" type="text/javascript"></script>
</body>
</html>
