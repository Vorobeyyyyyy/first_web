<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<jsp:useBean id="requestedUser" class="com.vorobyev.fwb.model.entity.User" scope="request"/>
<jsp:useBean id="commends" scope="request" type="java.util.List<com.vorobyev.fwb.model.entity.Commend>"/>
<jsp:useBean id="canEdit" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="isPublisher" scope="request" type="java.lang.Boolean"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>
<%@ page import="com.vorobyev.fwb.model.entity.UserRole" %>

<html>

<head>
    <title>Профиль</title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/profile_style.css" rel="stylesheet">
</head>

<body>
<%@ include file="header.jsp" %>
<div class="main_holder">
    <div class="section profile">
        <div id="avatar_button" class="<c:if test="${canEdit}">img_with_text</c:if>">
            <img src="${pageContext.request.contextPath}/.do?command=take_file&file_name=${requestedUser.avatarPath}"
                 alt="${requestedUser.login}" class="profile_image">
            <div class="text_on_cover"><fmt:message key="profile.change_avatar"/></div>
        </div>
        <div class="profile_information">
            <div class="profile_name">${requestedUser.login}</div>
            <div class="profile_text"><fmt:message key="profile.level"/>: <fmt:message
                    key="user.${requestedUser.role.toString().toLowerCase()}"/></div>
            <div class="profile_text"><fmt:message
                    key="profile.name"/>: ${requestedUser.firstName} ${requestedUser.secondName}</div>
            <div class="profile_text"><fmt:message key="profile.email"/>: ${requestedUser.email}</div>
        </div>
    </div>
    <c:if test="${isPublisher}">
        <div class="section">
            <div class="profile_button hoverable">
                <a href="${pageContext.request.contextPath}${WebPagePathPrepared.MAIN}&${WebPagePathPrepared.MAIN_PUBLISHER.formatted(requestedUser.login)}"
                 class="profile_button_text">Прейти к публикациям</a></div>
            <c:if test="${canEdit}">
                <div class="profile_button hoverable"><a
                        href="${pageContext.request.contextPath}${WebPagePathPrepared.GO_CREATE_PUBLICATION}"
                        class="profile_button_text">Создать публикацию</a></div>
            </c:if>
            <c:if test="${canEdit && requestedUser.role == UserRole.ADMIN}">
                <div class="profile_button hoverable"><a
                        href="${pageContext.request.contextPath}${WebPagePathPrepared.ADMIN_USERS}"
                        class="profile_button_text">Перейти к панели администратора</a></div>
            </c:if>
        </div>
    </c:if>
    <div class="section commends">
        <div class="section_title"><fmt:message key="profile.commends"/>:</div>
        <c:forEach var="commend" items="${commends}">
            <div class="commend">
                <a class="commended_publ custom_a"
                   href="${pageContext.request.contextPath}${WebPagePathPrepared.PUBLICATION_WITH_ID.formatted(commend.publicationId)}">${commend.publicationTitle}</a>
                <div class="commend_text">${commend.body}</div>
            </div>
        </c:forEach>
    </div>
</div>

<div id="avatar_change_modal" class="modal">
    <div class="modal-content">
        <span id="close_avatar_change" class="close">&times;</span>
        <form method="post"
              action="${pageContext.request.contextPath}/avatar.up"
              enctype="multipart/form-data" class="update_avatar_form"
              id="avatar_form">
            <input type="file" name="uploadFile">
            <input type="submit" class="submit_avatar" value="<fmt:message key="profile.submit" />">
        </form>
        <form method="post" action="${pageContext.request.contextPath}/avatar.do"
              id="avatar_form2" style="display: none">
            <input type="hidden" name="command" value="change_avatar">
            <input type="text" name="avatar_path" id="avatar_path" value="">
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/profile_script.js"></script>
</body>

</html>