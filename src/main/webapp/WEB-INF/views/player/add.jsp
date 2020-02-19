<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/17/2020
  Time: 7:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Add Player</title>
</head>
<body>
    <h2>Add player page</h2>

    <form:form action="${pageContext.request.contextPath}/player/add" modelAttribute="player">
        <label>Name: </label>
        <form:input path="name"/></br>

        <label>Date of Birth</label>
        <form:input path="dob"/></br>

        <label>Age: </label>
        <form:input path="age"/></br>

        <form:select path="countryId" items="${country_list}" itemValue="id" itemLabel="name"/>

        <input type="submit" name="submit" value="submit"/>
    </form:form>
</body>
</html>
