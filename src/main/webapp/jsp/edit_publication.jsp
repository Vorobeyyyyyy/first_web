<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>
<jsp:useBean id="publication" scope="request" class="com.vorobyev.fwb.entity.Publication"/>

<html>
<head>
    <title>Редактирование публикации</title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/create_publication_style.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="main_holder">
    <div class="news section" hidden>
        <div class="news_header">
            <div class="image_holder" id="main_image_holder">
                <img class="main_image changeable_img" id="main_image" alt=""
                     src="${pageContext.request.contextPath}${WebPagePathPrepared.TAKE_FILE.formatted(publication.previewImagePath)}">
            </div>
            <div class="news_header_text">
                <textarea class="news_topic" id="real_topic" placeholder="Title of the topic">${publication.title}</textarea>
                <div class="bottom_line">
                    <div class="bottom_line_text"><fmt:formatDate value="${publication.calendar.time}" type="date"
                                                                  dateStyle="long"/></div>
                    <div class="bottom_line_text"><fmt:formatDate value="${publication.calendar.time}" type="time"
                                                                  timeStyle="short"/></div>
                    <div class="bottom_line_text">${publication.publisherNickname}</div>
                </div>
            </div>
        </div>
        <div class="news_body" id="news_body" contenteditable="true">
            ${publication.content}
        </div>
    </div>
    <div class="buttons section">
        <button class="submit_button" id="submit_button" type="button">Submit</button>
        <button class="reject_button" type="button">Reject</button>
    </div>
    <form method="POST" action="publ.do" style="display: none;" id="hidden_form">
        <input type="hidden" name="command" value="edit_publication">
        <input type="hidden" name="publication_id" value="${publication.id}">
        <input type="text" id="form_img" name="image_path" value="">
        <textarea id="form_title" name="title"></textarea>
        <textarea id="form_content" name="content"></textarea>
        <textarea id="form_summary" name="summary"></textarea>
    </form>
    <form method="post"
          action="${pageContext.request.contextPath}/avatar.up"
          enctype="multipart/form-data" class="update_avatar_form"
          id="upload_form" style="display: none">
        <input type="file" name="uploadFile" id="file_select">
    </form>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/create_publication_script.js"></script>

</html>