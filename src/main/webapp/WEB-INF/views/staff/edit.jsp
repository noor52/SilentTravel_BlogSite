<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/19/2020
  Time: 12:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="../includes/dobdatepicker.jsp"/>
</head>
<body>

<h2> Edit Coaching Staff: </h2>
<form:form action="${pageContext.request.contextPath}/staff/edit" method="post" modelAttribute="staff">
    <form:input type="hidden" path="id" value="${staff.id}"/></br>
    <label>Name: </label>
    <form:input path="name" readonly="true"/></br>

    <label>Date of Birth</label>
    <form:input id="dobdatepicker" path="dob" /></br>

    <label>Age: </label>
    <form:input path="age"/></br>

    <label>Country: </label>
    <form:input path="countryName"  readonly="true"/></br>
    <form:input type="hidden" path="countryId" value ="${staff.countryId}"/></br>

    <input type="submit" name="submit" value="submit"/>
</form:form>
</body>
</html>
