<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>

<html>

<head>
    <title>
        <fmt:message key="login.title" />
    </title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/form_validation.js"></script>
</head>

<body>
<%@ include file="header.jsp"%>
<div class="main_holder">
    <div class="section">
        <div class="form_holder">
            <form action="${pageContext.request.contextPath}/login.do">
                <input type="hidden" name="command" value="login">
                <div class="main_title input_holder first">
                    <fmt:message key="login.title"/>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-user"></i>
                    <div class="input_label"><fmt:message key="login.login"/></div>
                    <input type="text" placeholder="Alex" name="login" invalidmessage="<fmt:message key="login.login_hint"/>" pattern=".{3,16}" required>
                </div>
                <div class="input_holder hoverable with_icon">
                    <i class="fas fa-key"></i>
                    <div class="input_label"><fmt:message key="login.password"/></div>
                    <input type="password" placeholder="password1234" name="password" invalidmessage="<fmt:message key="login.password_hint"/>" pattern=".{8,20}" required>
                </div>
                <button type="submit" class="input_holder hoverable last with_icon form_submit">
                    <i class="fas fa-sign-in-alt"></i>
                    <div><fmt:message key="login.submit"/></div>
                </button>
                <a href="${pageContext.request.contextPath}${WebPagePathPrepared.REGISTER}" class="secondary_link"><fmt:message key="login.go_register"/></a>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>

</html>