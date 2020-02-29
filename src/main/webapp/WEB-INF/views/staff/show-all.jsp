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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
    <title>Staff list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>


<section class="content">
    <div class="container-fluid">
        <div class="row">
            <%--left Menu START--%>
            <div class="left-menu-section col-lg-2 col-md-3 col-sm-4" >

                <jsp:include page="../includes/homemenu.jsp"/>

            </div>
            <%--left Menu END--%>

            <%--content body section START--%>
            <div class="right-body-section col-lg-10 col-md-9 col-sm-8">

                <%--nav section START--%>
                <div class="row">
                    <div class="col-sm-12">
                        <nav class="navbar navbar-light bg-light justify-content-between">
                            <a class="navbar-brand">ICC WEB</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
                        </nav>
                    </div>
                </div>
                <%--nav section END--%>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="card" style="margin-top: 10px">
                            <div class="card-header"> List of coaching staff</div>

                            <table class="table table-bordered">
                                <thead >
                                <tr>
                                    <th>Staff Name</th>
                                    <th>Age</th>
                                    <th>Date of Birth</th>
                                    <th>Country</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${staff_list}" var="staff">
                                    <tr>
                                        <td>${staff.name}</td>
                                        <td>${staff.age}</td>
                                        <td>${staff.dob}</td>
                                        <td>${staff.countryName}</td>
                                <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN','TEAM_MANAGER')">
                                    <td><a class="btn btn-warning" href="${pageContext.request.contextPath}/staff/edit?id=${staff.id}">Edit</a></td>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('SUPER_ADMIN','ADMIN',)">
                                    <td><a class="btn btn-danger" href="${pageContext.request.contextPath}/staff/delete?id=${staff.id}">Delete</a></td>
                                </sec:authorize>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>



                <%--footer START --%>
                <%--footer END--%>
            </div>
        </div>
        <%--content body section END--%>

    </div>
</section>


</body>
</html>
