<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/17/2020
  Time: 11:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>All team</title>
</head>
<body>

<h2>All Registered team</h2>

<table>
    <tr>
        <th>Team Name</th>
        <th>Country</th>
    </tr>
    <c:forEach items="${team_list}" var="team">
        <tr>
            <td>${team.name}</td>
            <td>${team.countryName}</td>
            <td><a href="${pageContext.request.contextPath}/team/add-team-player?team_id=${team.id}">Add Team Player</a></td>
            <td><a href="${pageContext.request.contextPath}/team/add-team-staff?team_id=${team.id}">Add Coaching Staff</a></td>
            <td><a href="${pageContext.request.contextPath}/team/edit?team_id=${team.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/team/delete?team_id=${team.id}">Delete</a></td>

        </tr>
    </c:forEach>
</table>

</body>
</html>
