<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<jsp:useBean id="publication" scope="request" class="com.vorobyev.fwb.model.entity.Publication"/>
<jsp:useBean id="locale" scope="session" type="java.lang.String"/>
<jsp:useBean id="commends" scope="request" type="java.util.List<com.vorobyev.fwb.model.entity.Commend>"/>
<%@ page import="com.vorobyev.fwb.controller.WebPagePathPrepared" %>

<html>
<head>
    <title>${publication.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/publication_style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <script>const likeCommand = "${pageContext.request.contextPath}/like.do";</script>
    <script>const srcPrefix = "${pageContext.request.contextPath}${WebPagePathPrepared.TAKE_FILE}" </script>
    <script>const isLogin = "${is_login}"</script>
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="main_holder">
    <div class="news section">
        <div class="news_header">
            <div class="image_holder">
                <img class="main_image"
                     src="${pageContext.request.contextPath}/getFile.do?command=take_file&file_name=${publication.previewImagePath}"
                     alt="${publication.title}">
            </div>
            <div class="news_header_text">
                <textarea class="news_topic unformatted_text" readonly>${publication.title}</textarea>
                <div class="bottom_line">
                    <div class="bottom_line_text"><fmt:formatDate value="${publication.calendar.time}" type="date"
                                                                  dateStyle="long"/></div>
                    <div class="bottom_line_text"><fmt:formatDate value="${publication.calendar.time}" type="time"
                                                                  timeStyle="short"/></div>
                    <div class="bottom_line_text">${publication.publisherNickname}</div>
                </div>
            </div>
        </div>
        <div class="news_body">
            ${publication.content}
        </div>
    </div>
    <div class="next_prev section">
        <button class="next_prev_button">Назад</button>
        <button class="next_prev_button">Вперёд</button>
    </div>
    <div class="section commends">
        <div class="commends_title">Комментарии:</div>
        <c:if test="${commends.size() == 0}">
            <div class="no_commends">
                Нет комментариев, ваш может быть первым!
            </div>
        </c:if>
        <c:forEach items="${commends}" var="commend">
            <div class="commend">
                <div class="commend_title">
                    <img class="commentator_img"
                         src="${pageContext.request.contextPath}${WebPagePathPrepared.TAKE_FILE.formatted(commend.publisherImage)}"
                         alt="${commend.publisherName}">
                    <a class="commentator_name custom_a" href="${pageContext.request.contextPath}${WebPagePathPrepared.SHOW_PROFILE.formatted(commend.publisherName)}">${commend.publisherName}</a>
                    <div class="comment_date"><fmt:formatDate value="${commend.date.time}" type="both"/></div>
                    <button class="like_button <c:if test="${commend.liked}">liked</c:if> " commendId="${commend.id}">
                        <i class="far fa-thumbs-up"></i>
                        <div class="like_count">${commend.likesCount}</div>
                    </button>
                </div>
                <div class="commend_text">${commend.body}</div>
            </div>
        </c:forEach>
    </div>
    <c:choose>
        <c:when test="${is_login}">
            <div class="section post_commend">
                <form method="POST" action="${pageContext.request.contextPath}/commend.do" class="post_commend_form">
                    <input type="hidden" name="command" value="add_commend">
                    <input type="hidden" name="publicationId" value="${publication.id}">
                    <textarea name="body" placeholder="Ограничение от 10 до 200 символов"
                              class="commend_body"></textarea>
                    <input type="submit" class="submit_commend" value="Комментировать">
                </form>
            </div>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/publication_script.js"></script>

</html>