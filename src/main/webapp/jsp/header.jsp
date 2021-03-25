<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>
<script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>

<header>
    <div class="header_content_holder">
        <a class="header_reference project_name" href="${pageContext.request.contextPath}${WebPagePathPrepared.MAIN}">4DPA</a>
        <a class="header_reference header_part hoverable" href="#"><fmt:message key="header.engineering"/></a>
        <a class="header_reference header_part hoverable" href="#"><fmt:message key="header.games"/></a>
        <a class="header_reference header_part hoverable" href="#"><fmt:message key="header.reviews"/></a>
        <form action="main.do" class="header_part search right">
            <input type="hidden" name="command" value="show_publications">
            <input name="search" class="search_field" type="text" placeholder="<fmt:message key="header.search"/>">
            <button class="search_submit" type="submit" value=""><i class="fas fa-search"></i></button>
        </form>
        <c:choose>
        <c:when test="${!is_login}">
        <a class="header_reference header_part hoverable with_icon" href="${pageContext.request.contextPath}/login.do?command=go_to_login"><i class="fas fa-key"></i><fmt:message key="header.login"/></a>
        </c:when>
        <c:otherwise>
        <div class="header_part dropdown hoverable with_icon">
            <i class="fas fa-user-circle"></i>${user.login}
            <div class="dropdown_content">
                <a href="${pageContext.request.contextPath}${WebPagePathPrepared.SHOW_PROFILE.formatted(user.login)}" class="dropdown_content_item"><fmt:message key="header.profile"/></a>
                <div class="dropdown_delimiter"></div>
                <a href="${pageContext.request.contextPath}/logout.do?command=logout" class="dropdown_content_item"><fmt:message key="header.logout"/></a>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
        <div class="header_part dropdown hoverable with_icon">
            <i class="fas fa-globe"></i><fmt:message key="header.language"/>
            <div class="dropdown_content">
                <a href="${pageContext.request.contextPath}/locale.do?command=set_locale&language=ru&prev_page=${currentUrl}" class="dropdown_content_item">Русский</a>
                <a href="${pageContext.request.contextPath}/locale.do?command=set_locale&language=en&prev_page=${currentUrl}" class="dropdown_content_item">English</a>
            </div>
        </div>
    </div>
</header>