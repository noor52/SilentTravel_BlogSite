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
    <title>Show players</title>
</head>
<body>
<table>
    <thead>
    <td>Player Name</td>
    <td>Age</td>
    <td>Date of Birth</td>
    <td>Country</td>
    </thead>
    <tbody>
    <c:forEach items="${player_list}" var="player">
        <tr>
            <td>${player.name}</td>
            <td>${player.age}</td>
            <td>${player.dob}</td>
            <td>${player.countryName}</td>
            <td><a href="${pageContext.request.contextPath}/player/edit?id=${player.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/player/delete?id=${player.id}">Delete</a></td>
        </tr>
    </c:forEach>

    </tbody>
</table>

</body>
</html>
