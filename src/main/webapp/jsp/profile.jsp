<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<jsp:useBean id="user" class="com.vorobyev.fwb.entity.User" scope="session"/>

<html>

<head>
    <title>Профиль</title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/profile_style.css" rel="stylesheet">
</head>

<body>
<%@ include file="header.jsp"%>
<div class="main_holder">
    <div class="section profile">
        <div id="avatar_button" class="img_with_text">
            <img src=${pageContext.request.contextPath}/.do?command=take_file&file_name=${user.avatarPath}
                 alt="${user.login}" class="profile_image">
            <div class="text_on_cover"><fmt:message key="profile.change_avatar"/></div>
        </div>
        <div class="profile_information">
            <div class="profile_name">${user.login}</div>
            <div class="profile_text"><fmt:message key="profile.level"/>: <fmt:message key="user.${user.level.toString().toLowerCase()}"/></div>
            <div class="profile_text"><fmt:message key="profile.name"/>: ${user.firstName} ${user.secondName}</div>
            <div class="profile_text"><fmt:message key="profile.email"/>: ${user.email}</div>
            <div class="profile_text"><fmt:message key="profile.phone"/>: ${user.phoneNumber}</div>
        </div>
    </div>
    <div class="section commends">
        <div class="section_title"><fmt:message key="profile.commends"/>:</div>
        <div class="commend">
            <div class="commended_publ">Сооснователь OnePlus анонсировал первое устройство «Ничего»</div>
            <div class="commend_text">Я тоже могу придумать ничего (пост не читал даже хаха)</div>
        </div>
        <div class="commend">
            <div class="commended_publ">Сооснователь OnePlus анонсировал первое устройство «Ничего»</div>
            <div class="commend_text">Я тоже могу придумать ничего (пост не читал даже хаха)</div>
        </div>
    </div>
    <div class="section subscibes">
        <div class="section_title"><fmt:message key="profile.subscribes"/>:</div>
        <div class="subsribe animate__animated">
            <div class="publisher_name">Cherepaha123</div>
            <button class="unsubscribe_btn"><fmt:message key="profile.unsub"/></button>
        </div>
    </div>
</div>

<div id="avatar_change_modal" class="modal">
    <div class="modal-content">
        <span id="close_avatar_change" class="close">&times;</span>
        <form method="post"
              action="${pageContext.request.contextPath}/change_avatar.up?command=change_avatar"
              enctype="multipart/form-data" class="update_avatar_form">
            <input type="hidden" name="username" value="${user.login}">
            <input type="file" name="uploadFile">
            <input type="submit" class="submit_avatar" value="<fmt:message key="profile.submit" />">
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/profile_script.js"></script>
</body>

</html>