<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/18/2020
  Time: 7:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Staff</title>
<jsp:include page="../includes/dobdatepicker.jsp"/>
</head>
<body>

<h2>Add Staff page</h2>

<form:form action="${pageContext.request.contextPath}/staff/add" modelAttribute="staff">
    <label>Name: </label>
    <form:input path="name"/></br>

    <label>Date of Birth</label>
    <form:input id="dobdatepicker" path="dob"/></br>

    <label>Age: </label>
    <form:input path="age"/></br>

    <label>Role: </label>
    <form:select path="role" items="${role_list}" />


    <form:select path="countryId" items="${country_list}" itemValue="id" itemLabel="name"/>

    <input type="submit" name="submit" value="submit"/>
</form:form>

</body>
</html>
