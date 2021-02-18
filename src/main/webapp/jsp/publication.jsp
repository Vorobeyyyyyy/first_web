<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="pagecontent"/>
<jsp:useBean id="publication" scope="request" class="com.vorobyev.fwb.entity.Publication"/>
<jsp:useBean id="locale" scope="session" type="java.lang.String"/>

<html>
<head>
    <title>${publication.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_style.css" >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/publication_style.css" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp"%>
    <div class="main_holder">
        <div class="news section">
            <div class="news_header">
                <img class="main_image" src="${pageContext.request.contextPath}${publication.previewImagePath}" alt="картинка">
                <div class="news_header_text">
                    <div class="news_topic">${publication.title}</div>
                    <div class="bottom_line">
                        <div class="bottom_line_text"><fmt:formatDate value="${publication.calendar.time}" type="date" dateStyle="long"/></div>
                        <div class="bottom_line_text"><fmt:formatDate value="${publication.calendar.time}" type="time" timeStyle="short"/></div>
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
            <div class="commends_title">Комментарии: </div>
            <div class="commend">
                <div class="commend_title">
                    <img class="commentator_img" src="avatar.jpg" alt="">
                    <div class="commentator_name">MrSkilk</div>
                    <div class="comment_date">10.02.2021</div>
                    <button class="like_button"><i class="far fa-thumbs-up"></i>21</button>
                </div>
                <div class="commend_text">Я тоже могу придумать ничего (пост не читал даже хаха)</div>
            </div>
            <div class="commend">
                <div class="commend_title">
                    <img class="commentator_img" src="avatar.jpg" alt="">
                    <div class="commentator_name">MrSkilk</div>
                    <div class="comment_date">10.02.2021</div>
                    <button class="like_button"><i class="far fa-thumbs-up"></i>21</button>
                </div>
                <div class="commend_text">Я тоже могу придумать ничего (пост не читал даже хаха)</div>
            </div>
            <div class="commend">
                <div class="commend_title">
                    <img class="commentator_img" src="avatar.jpg" alt="">
                    <div class="commentator_name">MrSkilk</div>
                    <div class="comment_date">10.02.2021</div>
                    <button class="like_button"><i class="far fa-thumbs-up"></i>21</button>
                </div>
                <div class="commend_text">Я тоже могу придумать ничего (пост не читал даже хаха)</div>
            </div>
        </div>
    </div>
</body>
<script src="${pageContext.request.contextPath}/js/publication_script.js"></script>

</html>