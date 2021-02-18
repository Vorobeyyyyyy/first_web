<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<head>
    <title><fmt:message key="register.title"/></title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp"%>
<h1>
    <fmt:message key="register.title"/>
</h1>
${error_message}
<form action="register.do" method="get">
    <input type="hidden" name="command" value="register">
    <div class="mb-3">
        <label for="login-field" class="form-label"><fmt:message key="register.login"/></label>
        <input type="text" name="login" class="form-control" id="login-field"
               title="<fmt:message key="register.login_hint"/>" required="required">
    </div>

    <div class="mb-3">
        <label for="email-field" class="form-label"><fmt:message key="register.email"/></label>
        <input type="email" name="email" class="form-control" id="email-field"
               title="<fmt:message key="register.email_hint"/>"
               required="required" placeholder="example@domain.com">
    </div>

    <div class="row">
        <div class="col">
            <input type="text" name="first_name" class="form-control"
                   placeholder="<fmt:message key="register.first_name"/>"
                   required="required">
        </div>
        <div class="col">
            <input type="text" name="second_name" class="form-control"
                   placeholder="<fmt:message key="register.second_name"/>"
                   required="required">
        </div>
    </div>

    <div class="mb-3">
        <label for="phone-field" class="form-label"><fmt:message key="register.phone"/></label>
        <input type="text" name="phone_number" class="form-control" id="phone-field" pattern="+[0-9]{12}"
               title="<fmt:message key="register.phone_hint"/>" required="required" placeholder="+375292222222">
    </div>

    <div class="mb-3">
        <label for="password-field" class="form-label"><fmt:message key="register.password"/></label>
        <input type="password" name="password" class="form-control" id="password-field" pattern=".{8,32}"
               title="<fmt:message key="register.password_hint"/>" required="required">
    </div>
    <fmt:message key="register.password_repeat"/>
    <input type="password" name="repeat_password">
    <br>
    <input type="submit" value="<fmt:message key="register.submit"/>">
</form>
<form action="${pageContext.request.contextPath}/login.do" method="get">
    <input type="hidden" name="command" value="go_to_login">
    <input type="submit" value="<fmt:message key="register.go_to_login"/>">
</form>
</body>
<script src="" type="module"></script>
</html>