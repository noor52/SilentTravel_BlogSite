<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/16/2020
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Country</title>
</head>
<body>
<h2>Add new Country</h2>

<form:form action="${pageContext.request.contextPath}/country/add" method="post">
    <label>Country Name: </label>
    <input type="text" name="country_name" placeholder="Country"/>
    <input type="submit" name="submit" value="Add">
</form:form>


</body>
</html>
