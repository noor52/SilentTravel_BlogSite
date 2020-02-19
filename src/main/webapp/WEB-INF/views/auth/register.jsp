<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/17/2020
  Time: 7:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Register page</title>
</head>
<body>
<h2>Rgistration page</h2>
    <form:form action="${pageContext.request.contextPath}/auth/register" method="post" modelAttribute="user">
        <label>Username: </label>
        <form:input path="name"/>
        <label>Password: </label>
        <form:input path="password"/>
        <form:select path="role" items="${role_list}"/>
        <input type="submit" name="sumbmit" value="submit"/>
    </form:form>

</body>
</html>
