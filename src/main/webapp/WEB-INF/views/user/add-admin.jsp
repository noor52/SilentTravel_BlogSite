<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/19/2020
  Time: 11:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>

    <h2> Add new Admin</h2>
    <form:form action="${pageContext.request.contextPath}/user/add-admin" method="post">
        <label>User name: </label>
        <input name="username" type="text">
        <input type="submit" name="submit" value="Add Admin">
    </form:form>
</body>
</html>
