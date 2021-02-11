<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="user" class="com.vorobyev.fwb.entity.User" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Title</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#"><fmt:message key="header.title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/main.do?command=go_to_main"><fmt:message
                            key="header.main"/></a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle language-selector"
                                type="button" id="dropdownMenu1" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="header.language"/>
                        </button>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/locale.do?command=set_locale&language=ru&prev_page=${pageContext.request.requestURI}">Русский</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/locale.do?command=set_locale&language=en&prev_page=${pageContext.request.requestURI}">English</a>
                        </div>
                    </div>
                </li>
                <li class="nav-item" ${!is_login?"hidden=\"hidden\"":""}>
                    <ul class="navbar-nav ml-auto user-mini-profile pl-2">
                        <li>
                            <input type="image" src="${pageContext.request.contextPath}/image/avatar.jpg"
                                   class="mini-avatar pt-1" alt="Avatar">
                        </li>
                        <li>
                            <div class="dropdown">
                                <button class="btn"
                                        type="button" id="mini-profile-dropdown" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    Username
                                </button>
                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="mini-profile-dropdown">
                                    <a class="dropdown-item" href="#!">Real Name</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="#!">userLevel</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
                <li>
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/login.do?command=go_to_login#" ${is_login?"hidden=\"hidden\"":""}><fmt:message
                            key="header.login"/></a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>
</html>
