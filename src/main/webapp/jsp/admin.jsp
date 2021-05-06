<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>
<html>

<head>
    <title>Admin panel</title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/admin_style.css" rel="stylesheet">

</head>

<body>
<%@ include file="header.jsp" %>
<div class="main_holder">
    <div class="admin section" hidden>
        <div class="section_title">Панель администратора</div>
        <div class="module_selector">
            <a class="module_button hoverable"
               href="${pageContext.request.contextPath}${WebPagePathPrepared.ADMIN_USERS}">Пользователи</a>
            <a class="module_button hoverable"
               href="${pageContext.request.contextPath}${WebPagePathPrepared.ADMIN_PUBLICATIONS}">Публикации</a>
            <a class="module_button hoverable"
               href="${pageContext.request.contextPath}${WebPagePathPrepared.ADMIN_COMMENDS}">Комментарии</a>
        </div>
        <c:if test="${users != null}">
            <div>
                <form class="filter d_none">
                    <input type="text" class="text_filter" placeholder="Username">
                    <div class="checkbox_holder">
                        <div class="checkbox_text">User<input type="checkbox"></div>
                        <div class="checkbox_text">Publisher<input type="checkbox"></div>
                        <div class="checkbox_text">Admin<input type="checkbox"></div>
                    </div>
                    <div class="checkbox_holder">
                        <div class="checkbox_text" style="margin: auto 0px;">Banned?<input type="checkbox"></div>
                    </div>
                    <button class="filter_button hoverable">Фильтровать</button>
                </form>
                <c:forEach items="${users}" var="user">
                    <div class="entity">
                        <img class="entity_preview"
                             src="${pageContext.request.contextPath}${WebPagePathPrepared.TAKE_FILE.formatted(user.avatarPath)}"
                             alt="avatar">
                        <div class="entity_text">${user.email}</div>
                        <div class="entity_text">${user.firstName} ${user.secondName}</div>
                        <div class="entity_text">${user.email}</div>
                        <a href="${pageContext.request.contextPath}${WebPagePathPrepared.REMOVE_USER.formatted(user.login)}"
                           class="entity_button hoverable">delete</a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${publications != null}">
            <div>
                <form class="filter d_none">
                    <input type="text" class="text_filter" placeholder="Topic">
                    <input type="text" class="text_filter" placeholder="Username">
                    <div class="text_date">От<input type="date" name="date_from"></div>
                    <div class="text_date">До<input type="date" name="date_to"></div>
                    <button type="submit" class="filter_button hoverable">Фильтровать</button>
                </form>
                <c:forEach items="${publications}" var="publication">
                    <div class="entity">
                        <img class="entity_preview"
                             src="${pageContext.request.contextPath}${WebPagePathPrepared.TAKE_FILE.formatted(publication.previewImagePath)}"
                             alt="preview">
                        <div class="entity_text">${publication.title}</div>
                        <div class="entity_text">${publication.publisherNickname}</div>
                        <div class="entity_text"><fmt:formatDate value="${publication.calendar.time}" type="date"
                                                                 dateStyle="long"/></div>
                        <a href="${pageContext.request.contextPath}${WebPagePathPrepared.EDIT_PUBLICATION.formatted(publication.id)}"
                           class="entity_button hoverable" style="margin-left: auto;">edit</a>
                        <a href="${pageContext.request.contextPath}${WebPagePathPrepared.REMOVE_PUBLICATION.formatted(publication.id)}"
                           class="entity_button hoverable">delete</a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${commends != null}">
            <div>
                <form class="filter d_none">
                    <input type="text" class="text_filter" placeholder="Topic">
                    <input type="text" class="text_filter" placeholder="Username">
                    <input type="number" min="0" class="number_filter" placeholder="От">
                    <input type="number" min="0" class="number_filter" placeholder="До">
                    <div class="text_date">От<input type="date" name="date_from"></div>
                    <div class="text_date">До<input type="date" name="date_to"></div>
                    <button type="submit" class="filter_button hoverable">Фильтровать</button>
                </form>
                <c:forEach items="${commends}" var="commend">
                    <div class="entity">
                        <div class="entity_text">${commend.publicationTitle}</div>
                        <div class="entity_text">${commend.publisherName}</div>
                        <div class="entity_text">${commend.likesCount}</div>
                        <div class="entity_text"><fmt:formatDate value="${commend.date.time}" type="date"
                                                                 dateStyle="long"/></div>
                        <button class="entity_button hoverable" style="margin-left: auto;"
                                onclick="alert('${commend.body}')">show
                        </button>
                        <a href="${pageContext.request.contextPath}${WebPagePathPrepared.REMOVE_COMMEND.formatted(commend.id)}"
                           class="entity_button hoverable">delete</a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>