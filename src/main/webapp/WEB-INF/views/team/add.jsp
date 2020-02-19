<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/17/2020
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add team</title>
</head>
<body>

<form:form action="${pageContext.request.contextPath}/team/add" method="post" modelAttribute="team">
    <label>Team Name: </label>
    <form:input path="name" /></br>
    <form:select path="coundtryId" items="${country_list}" itemValue="id" itemLabel="name"/></br>
    <input type="submit" value="Add Team" name="submit">
</form:form>


</body>
</html>
