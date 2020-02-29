<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/18/2020
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add team staff</title>
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
                    <div class="col-lg-4 col-md-6 col-sm-12">
                        <div class="card" style="margin-top: 10px">
                            <div class="card-body">
                                <h5 class="card-title">Add team staff</h5>
                                <form:form  class="form-group" action="${pageContext.request.contextPath}/team/add-team-staff" method="post" modelAttribute="idList">
                                    <input type="hidden" name="team_id" value="${team.id}"/>

                                    <div class="row">
                                        <div class="form-group col-sm-12">
                                            <label for="team_name"> Team: ${team.name}</label>
                                            <form:select id="team_name" class="form-control" multiple="true" path="ids" items="${staffs}" itemValue="id" itemLabel="name"/>
                                        </div>
                                    </div>


                                    <input class="btn btn-primary float-right" type="submit" name="submit" value="Submit" style="margin-top: 10px"/>
                                </form:form>
                            </div>
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

