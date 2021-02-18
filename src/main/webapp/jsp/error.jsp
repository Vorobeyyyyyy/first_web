<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>

<html>

<head>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error_style.css">
</head>
<body>
<div class="main_holder">
    <div class="section">
        <div class="error_word">
            <fmt:message key="error.error"/>
            <div class="error_number"><%=response.getStatus()%>
            </div>
        </div>
        <div class="error_description"><fmt:message key="error.${pageContext.response.status}"/>
        </div>
        <c:if test="<%=exception != null %>">
            <c:if test="<%=exception.getMessage() != null %>">
                <div class="error_description"><%=exception.getMessage()%>
                </div>
            </c:if>
            <div class="error_description"><%=exception.getClass()%>
            </div>
            <div class="exception_trace"><%
                StringBuilder builder = new StringBuilder();
                Arrays.stream(exception.getStackTrace()).forEach(o -> {
                    builder.append(o);
                    builder.append("<br>");
                });
            %>
                <%=builder%>
            </div>
        </c:if>
    </div>
</div>
</body>

</html>