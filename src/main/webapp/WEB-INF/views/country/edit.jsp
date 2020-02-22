<%--
  Created by IntelliJ IDEA.
  User: bashir
  Date: 2/18/2020
  Time: 1:05 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit country</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
                    <div class="col-lg-6 col-md-6 col-sm-12">
                        <div class="card" style="margin-top: 10px">

                            <div class="card-body">
                                <h5 class="card-title">Edit Country information</h5>

                                <form:form  class="form-group" action="${pageContext.request.contextPath}/country/edit" method="post" modelAttribute="country" >

                                    <form:input type="hidden" path="id" value="${country.id}" />

                                    <div class="form-group row">
                                        <div class="col-sm-12">
                                            <label for="countryname">Name</label>
                                            <form:input id="countryname" class="form-control" path="name"/>
                                        </div>

                                    </div>

                                    <div class="form-group row">
                                        <div class="col-sm-12">
                                            <label>Status   </label>

                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" id="active" name="isActive" value="true"/>
                                               <%-- <form:radiobutton class="form-check-input" id="active" path="isActive" value="true"/>--%>
                                                <label class="form-check-label" for="active">Active</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" id="inactive" name="isActive" value="false"/>
                                              <%--  <form:radiobutton class="form-check-input" id="inactive" path="isActive" value="false"/>--%>
                                                <label class="form-check-label" for="inactive">InActive</label>
                                            </div>
                                        </div>
                                    </div>


                                    <input class="btn btn-primary float-right" type="submit" name="submit" value="Save Country" style="margin-top: 10px"/>

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
