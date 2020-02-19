<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/16/2020
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Show countries</title>
</head>
<body>
<h2>List of countries</h2>
    <table>
        <thead>
        <td>Country Name</td>
        </thead>
        <tbody>
        <c:forEach items="${countryList}" var="country">

            <tr>
                <td>${country.name}</td>
                <td><a href="${pageContext.request.contextPath}/country/edit?id=${country.id}">Edit</a> </td>
                <td><a href="${pageContext.request.contextPath}/country/delete?id=${country.id}">Delete</a></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</body>
</html>
