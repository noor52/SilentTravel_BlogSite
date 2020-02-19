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

</head>
<body>

<h2> Edit Player: s</h2>
<form:form action="${pageContext.request.contextPath}/player/edit" method="post" modelAttribute="player">
    <form:input type="hidden" path="id" value="${player.id}"/></br>
    <label>Name: </label>
    <form:input path="name" readonly="true"/></br>

    <label>Date of Birth</label>
    <form:input path="dob" /></br>

    <label>Age: </label>
    <form:input path="age"/></br>

    <label>Country: </label>
    <form:input path="countryName"  readonly="true"/></br>
    <form:input type="hidden" path="countryId" value ="${player.countryId}"/></br>
    <%--    <form:select path="countryId" items="${country_list}" itemValue="id" itemLabel="name"/>--%>

    <input type="submit" name="submit" value="submit"/>
</form:form>
</body>
</html>
