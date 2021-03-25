<html>

<head>
    <title>Admin panel</title>
    <link href="${pageContext.request.contextPath}/css/common_style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/admin_style.css" rel="stylesheet">
    
</head>

<body>
    <div class="main_holder">
        <div class="admin section" hidden>
            <div class="section_title">Панель администратора</div>
            <div class="module_selector">
                <button class="module_button hoverable">Пользователи</button>
                <button class="module_button hoverable">Публикации</button>
                <button class="module_button hoverable">Комментарии</button>
            </div>
            <div>
                <form class="filer">
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
                <div class="entity">
                    <img class="entity_preview" src="2.png" alt="avatar">
                    <div class="entity_text">MrSkilk</div>
                    <div class="entity_text">User</div>
                    <div class="entity_text">sasah01@mail.ru</div>
                    <button class="entity_button hoverable" style="margin-left: auto;">BAN</button>
                    <button class="entity_button hoverable">edit</button>
                    <button class="entity_button hoverable">delete</button>
                </div>
            </div>
            <div>
                <form class="filer">
                    <input type="text" class="text_filter" placeholder="Topic">
                    <input type="text" class="text_filter" placeholder="Username">
                    <div class="text_date">От<input type="date" name="date_from"></div>
                    <div class="text_date">До<input type="date" name="date_to"></div> 
                    <button type="submit" class="filter_button hoverable">Фильтровать</button>
                </form>
                <div class="entity">
                    <img class="entity_preview" src="2.jpg" alt="preview">
                    <div class="entity_text">Название новости</div>
                    <div class="entity_text">MrSkilk</div>
                    <div class="entity_text">25.03.2021</div>
                    <button class="entity_button hoverable" style="margin-left: auto;">edit</button>
                    <button class="entity_button hoverable">delete</button>
                </div>
            </div>
            <div>
                <form class="filer">
                    <input type="text" class="text_filter" placeholder="Topic">
                    <input type="text" class="text_filter" placeholder="Username">
                    <input type="number" min="0" class="number_filter" placeholder="От">
                    <input type="number" min="0" class="number_filter" placeholder="До">
                    <div class="text_date">От<input type="date" name="date_from"></div>
                    <div class="text_date">До<input type="date" name="date_to"></div> 
                    <button type="submit" class="filter_button hoverable">Фильтровать</button>
                </form>
                <div class="entity">
                    <div class="entity_text">Название новости</div>
                    <div class="entity_text">MrSkilk</div>
                    <div class="entity_text">21</div>
                    <div class="entity_text">25.03.2021</div>
                    <button class="entity_button hoverable" style="margin-left: auto;">show</button>
                    <button class="entity_button hoverable">edit</button>
                    <button class="entity_button hoverable">delete</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>