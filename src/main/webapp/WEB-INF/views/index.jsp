<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/15/2020
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<br>
<head>
    <title>ICC Web</title>
</head>
<>

<h2>Welcome to ICC  </h2>

<a href="${pageContext.request.contextPath}/country/add">Add New Country</a>
<a href="${pageContext.request.contextPath}/country/show-all">Show countries</a>
</br>
<a href="${pageContext.request.contextPath}/team/add">Add New Team</a>
<a href="${pageContext.request.contextPath}/team/show-all">Show Teams</a>
</br>

<a href="${pageContext.request.contextPath}/player/add">Add Player</a>
<a href="${pageContext.request.contextPath}/player/show-all">Show players</a>

</br>

<a href="${pageContext.request.contextPath}/staff/add">Add staff</a>
<a href="${pageContext.request.contextPath}/staff/show-all">Show staffs</a>

<h3>See Team Members</h3>
<form:form action="${pageContext.request.contextPath}/team/team-members" method="post" modelAttribute="country">
    <form:select path="id" items="${countryList}" itemLabel="name" itemValue="id"/>
    <input type="submit" name="submit" value="See members"/>
</form:form>


<a href="${pageContext.request.contextPath}/user/add-admin">Add Admin</a>

</body>
</html>
