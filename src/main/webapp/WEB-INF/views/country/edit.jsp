<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/18/2020
  Time: 1:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Country</title>
</head>
<body>
<form:form action="${pageContext.request.contextPath}/country/edit" method="post" modelAttribute="country">
    <form:input type="hidden" path="id" value="${country.id}" />
    <label>Country Name:</label><form:input path="name"/></br>
    <input type="radio" id="active" name="isActive" value="true"/><label for="active">Active</label>
    <input type="radio" id="inactive" name="isActive" value="false"/><label for="inactive">InActive</label>
    <input type="submit" name="submit" value="Save Country">
</form:form>
</body>
</html>
