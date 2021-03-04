<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>

<html>

<head>
    <title>
        <fmt:message key="register.title"/>
    </title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/form_validation.js"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="main_holder">
    <div class="section">
        <div class="form_holder">
            <form action="${pageContext.request.contextPath}/register.do" method="post">
                <input type="hidden" name="command" value="register">
                <div class="main_title input_holder first">
                    <fmt:message key="register.title"/>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-user"></i>
                    <div class="input_label"><fmt:message key="register.login"/></div>
                    <input type="text" name="login" placeholder="Alex123" pattern=".{3,16}" required>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-key"></i>
                    <div class="input_label"><fmt:message key="register.password"/></div>
                    <input type="password" name="password" placeholder="password1234" pattern=".{8,20}" required>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-key"></i>
                    <div class="input_label"><fmt:message key="register.password_repeat"/></div>
                    <input type="password" placeholder="password1234">
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-signature"></i>
                    <div class="input_label"><fmt:message key="register.first_name"/></div>
                    <input type="text" name="first_name" placeholder="<fmt:message key="register.first_name_placeholder"/>"
                    pattern="[a-zA-Zа-яА-Я]{2,20}" required>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-signature"></i>
                    <div class="input_label"><fmt:message key="register.second_name"/></div>
                    <input type="text" name="second_name" placeholder="<fmt:message key="register.second_name_placeholder"/>"
                    pattern="[a-zA-Zа-яА-Я]{2,20}" required>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-envelope"></i>
                    <div class="input_label"><fmt:message key="register.email"/></div>
                    <input type="email" name="email" placeholder="alex@mail.com" pattern="[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+">
                </div>
                <button type="submit" class="input_holder hoverable last with_icon form_submit">
                    <i class="fas fa-user-plus"></i>
                    <div><fmt:message key="register.submit"/></div>
                </button>
                <a href="${pageContext.request.contextPath}${WebPagePathPrepared.LOGIN}"
                   class="secondary_link"><fmt:message key="register.go_login"/></a>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>

</html>