<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<head>
    <title><fmt:message key="login.title"/></title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp"%>
${error_message}
<form action="login.do" method="post" class="login-form">
    <input type="hidden" name="command" value="login">
    <div class="mb-3">
        <label for="login-field" class="form-label"><fmt:message key="login.login"/></label>
        <input type="text" name="login" class="form-control" id="login-field" pattern="[A-Za-z0-9]{4,16}"
               title="<fmt:message key="login.login_hint"/>" required="required">
    </div>
    <div class="mb-3">
        <label for="password-field" class="form-label"><fmt:message key="login.password"/></label>
        <input type="password" name="password" class="form-control" id="password-field" pattern=".{8,32}"
               title="<fmt:message key="login.password_hint"/>" required="required">
    </div>
    <div class="mb-3">
        <button type="submit" class="btn btn-primary login-submit-button"><fmt:message key="login.submit"/></button>
    </div>
</form>
<form action="register.do" method="get">
    <input type="hidden" name="command" value="go_to_register">
    <input type="submit" value="Sing up">
</form>
<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>