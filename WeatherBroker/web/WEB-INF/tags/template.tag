<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="title" fragment="true" %>
<html>
<head>
    <title><jsp:invoke fragment="title"/></title>
    <!-- Bootstrap -->
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />

    <!-- CSS -->
    <spring:url value="/resources/css/modern-business.css" var="startertemplate"/>
    <link href="${startertemplate}" rel="stylesheet" />

    <!-- Fonts -->
    <spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontawesome"/>
    <link href="${fontawesome}" rel="stylesheet" />

    <!-- JQuery -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>


</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.html">Wellcom to page</a>
        </div>
    </div>
</nav>


<jsp:doBody/>


<div class="container">
    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p>Copyright © Хмель А.В. 2018</p>
            </div>
        </div>
    </footer>
</div>

</body>

</html>