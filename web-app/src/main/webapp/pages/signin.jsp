<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/materialize.min.css"/>" type="text/css" rel="stylesheet" media="screen,projection">
    <title>Sign in</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col s4 offset-s4" style="margin-top: 15%">
            <sec:authorize access="!isAuthenticated()">
                <h2 class="center">Вход</h2>
                <c:url value="/j_spring_security_check" var="loginUrl" />
                <form action="${loginUrl}" method="post">
                    <input type="text" class="form-control" name="j_username" placeholder="user name"
                           value="root" required autofocus>
                    <input type="password" class="form-control" name="j_password" placeholder="Password"
                           value="root" required>
                    <button class="btn waves-effect waves-light" type="submit">Войти</button>
                </form>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <p>Логин: <sec:authentication property="principal.username" /></p><br>
                <a class="btn waves-effect waves-light" href="/logout" role="button">Выйти</a>
            </sec:authorize>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/materialize.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jq.js"/>" type="text/javascript"></script>
</body>
</html>
