<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.leader.servlets.Page" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">

<head>
	<meta http-equiv="content-type" content="text/html; charset=utf8" />
	<meta name="author" content="Andrey" />
    <script type="text/javascript" language="javascript" src="template/js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function()
         {
	           //slides the element with class "menu_body" when paragraph with class "menu_head" is clicked 
	           $("#firstpane p.menu_head").click(function()
            {
		      $(this).css({backgroundImage:"url(down.png)"}).next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
            $(this).siblings().css({backgroundImage:"url(left.png)"});
	       });
        	//slides the element with class "menu_body" when mouse is over the paragraph
	       $("#secondpane p.menu_head").mouseover(function()
            {
	           $(this).css({backgroundImage:"url(down.png)"}).next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
                $(this).siblings().css({backgroundImage:"url(left.png)"});
	       });
        });
</script>
    <link rel="stylesheet" type="text/css" href="/template/css/style.css" />
	<title>Lider</title>
</head>

<body>
<div style="position: absolute; width: 100%; z-index: 1;">
    <div class="reklama_left"></div>
    <div class="reklama_right">Блок реклами</div></div>
<div class="main_block">
    <!--ШАПКА САЙТА начало-->
    <div class="header">
        <div class="logo"><a href="#"><img src="images/logo.png" /></a></div>
        <div class="right_head_menu">
            <span>Головна&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Карта сайту&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Зворотній звязок&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Гостьова&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Контакти</span>
            <div class="right_find_block">
            
            </div>
        </div>
    </div>
    <!--ШАПКА САЙТА конец-->
    <!--ГОРИЗОНТАЛЬНОЕ МЕНЮ начало-->
    <div class="top_menu">
<ul id="nav">
	<li>
		<a href="#" title="Вернуться на главную страницу">Музика</a>
	</li>
	<li>
		<a href="#" title="Информация о компании">Фільми</a>
		<ul>
			<li><a href="#">Кліпи</a></li>
			<li><a href="#">Серіали</a></li>
		</ul>
	</li>
	<li>
		<a href="#" title="Что мы можем для вас сделать">Ігри</a>
		<ul>
			<li><a href="#">Категорія 1</a></li>
			<li><a href="#">Категорія 2</a></li>
			<li><a href="#">Категорія 3</a></li>
			<li><a href="#">Категорія 4</a></li>
		</ul>
	</li>
	<li>
		<a href="#" title="Наша продуктовая линейка">Книги</a>
	</li>
	<li>
		<a href="#" title="Как с нами связаться">Інтернет магазини</a>
	</li>
    <li>
		<a href="#" title="Как с нами связаться">Купівля/продаж</a>
	</li>
    <li>
		<a href="#" title="Как с нами связаться">Прогноз погоди</a>
	</li>
    <li>
		<a href="#" title="Как с нами связаться">Програми</a>
	</li>
</ul>

    </div>
    <!--ГОРИЗОНТАЛЬНОЕ МЕНЮ конец-->
    <!--ЛЕВОЕ ВЕРТИКАЛЬНОЕ МЕНЮ начало-->
    <div class="left_menu">
        <div style="float:left; margin-left:20px;"> <!--This is the second division of right-->
    <div class="menu_list" id="secondpane"> <!--Code for menu starts here-->
		<p class="menu_head">Мультимедія</p>
		<div class="menu_body">
		<a href="#">Музика</a>
         <a href="#">Фільми</a>
         <a href="#">Кліпи</a>	
		</div>
		<p class="menu_head">Ігри</p>
		<div class="menu_body">
			<a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>	
		</div>
		<p class="menu_head">Книги</p>
		<div class="menu_body">
          <a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>			
       </div>
       <p class="menu_head">Інтернет магазини</p>
		<div class="menu_body">
          <a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>			
       </div>
       <p class="menu_head">Купівля/продаж</p>
		<div class="menu_body">
          <a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>			
       </div>
       <p class="menu_head">Прогноз погоди</p>
		<div class="menu_body">
          <a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>			
       </div>
       <p class="menu_head">Пошук роботи</p>
		<div class="menu_body">
          <a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>			
       </div>
       <p class="menu_head">Програми</p>
		<div class="menu_body">
          <a href="#">Link-1</a>
         <a href="#">Link-2</a>
         <a href="#">Link-3</a>			
       </div>
  </div>      <!--Code for menu ends here-->
</div>
    </div>
    <!--ЛЕВОЕ ВЕРТИКАЛЬНОЕ МЕНЮ конец-->
    <!--ПРАВОЕ ВЕРТИКАЛЬНОЕ МЕНЮ начало-->
    <div class="right_menu">
        <div class="r_menu">
            <div style="font-family: sans-serif; font-size: 13px; text-align: center; position: relative; top: -10px; font-weight:bold; color: #006d9b;">Пропозиції в категорії</div>
            <div class="links_right_cat"><span style="font-size: 14px; font-weight:bold;">Фільми:</span><br/><a href="#">1.EX.UA</a><br/><a href="#">2.FILM.UA</a><br/><a href="#">3.VIDEO.COM.UA</a></div>
        </div>
    </div>
    <!--ПРАВОЕ ВЕРТИКАЛЬНОЕ МЕНЮ конец-->
    <!--БЛОК КОНТЕНТА начало-->
    <div class="content">
        <!--БЛОК РЕЙТИНГОВОГО ОКНА КОНТЕНТА начало-->

        <c:choose>

            <c:when test="${pageType == page.siteInfoPage}">
                <jsp:include page="siteDescription.jsp"/>
            </c:when>

            <c:when test="${pageType == page.listPage}">
                <jsp:include page="siteList.jsp"/>
            </c:when>

            <c:otherwise>
            </c:otherwise>

        </c:choose>



        <!--БЛОК РЕЙТИНГОВОГО ОКНА КОНТЕНТА конец-->
    </div>
    <!--БЛОК КОНТЕНТА конец-->
    <!--ФУТЕР начало-->
    <div class="footer">
        <div class="left_partners">
            <span style="font-weight: bold; font-size: 14px; margin-bottom: 10px;">Наші партнери:</span><p>Інформаційний портал - <a href="#">ukr.net</a></p>
            <p>Портал новин - <a href="#">vesti.ua</a></p>
            <p>Фітнес та спорт - <a href="#">sportik.com.ua</a></p>
        </div>
        <div class="copyright">
            All rights reserved;
        </div>
    </div>
    <!--ФУТЕР конец-->
</div>

</body>
</html>