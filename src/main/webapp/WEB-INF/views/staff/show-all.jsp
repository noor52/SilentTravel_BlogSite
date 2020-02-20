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
    <title>Show all staff</title>
</head>
<body>
<table>
    <thead>
    <td>Staff Name</td>
    <td>Age</td>
    <td>Date of Birth</td>
    <td>Country</td>
    </thead>
    <tbody>
    <c:forEach items="${staff_list}" var="staff">

        <tr>
            <td>${staff.name}</td>
            <td>${staff.age}</td>
            <td>${staff.dob}</td>
            <td>${staff.countryName}</td>
            <td><a href="${pageContext.request.contextPath}/staff/edit?id=${staff.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/staff/delete?id=${staff.id}">Delete</a></td>
        </tr>
    </c:forEach>

    </tbody>
</table>


</body>
</html>
