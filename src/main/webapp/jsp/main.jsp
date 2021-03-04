<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<jsp:useBean id="news" scope="request" type="java.util.List<com.vorobyev.fwb.entity.Publication>"/>

<html>
<head>
    <title><fmt:message key="main.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main_style.css">
</head>

<body>
<%@ include file="header.jsp" %>
<div class="main_holder">
    <c:if test="${publisher != null}">
        <div class="section centred">
            <div class="section_title">
                Публикации пользователя ${publisher}
            </div>
        </div>
    </c:if>
    <c:forEach items="${news}" var="item">
        <div class="section news_element">
            <div>
                <img class="news_image"
                     src="${pageContext.request.contextPath}/getFile.do?command=take_file&file_name=${item.previewImagePath}"
                     alt="${item.title}">
            </div>
            <div class="news_text">
                <div class="news_topic">
                    <a class="custom_a" href="${pageContext.request.contextPath}/publication.do?command=go_to_publication&publication_id=${item.id}">
                            ${item.title}
                    </a>
                </div>
                <div class="news_subtext">
                        ${item.summary}
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>