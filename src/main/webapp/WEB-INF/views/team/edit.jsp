<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/19/2020
  Time: 5:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Team</title>
</head>
<body>

<form:form action="${pageContext.request.contextPath}/team/edit" method="post" modelAttribute="team">
    <form:input type="hidden" path="id" value="${team.id}" />
    <label>Team Name: </label>
    <form:input path="name" /></br>
    <label>Team Name: </label>
    <form:input path="countryName" readonly="true"/>
    <form:input path="countryId" type="hidden" value="${team.countryId}" />
    <%--<form:select path="countryId" items="${country_list}" itemValue="id" itemLabel="name"/></br>--%>
    <input type="submit" value="Save Team" name="submit">
</form:form>



</body>
</html>
