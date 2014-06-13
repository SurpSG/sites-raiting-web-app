<%--
  Created by IntelliJ IDEA.
  User: ruki
  Date: 11.06.2014
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="content_block_text">

    <div class="content_block_pic">${site_info.pictureURL}</div>

    <div class="head_text"><c:out value="${site_info.name}"/></div>
    <br>

    <div class="info_block">
        Рейтинг: *****<c:out value="${site_info.raiting}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Відвідуваність:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Яндекс:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Гугл:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    <br>

    <div class="content_text"><c:out value="${site_info.description}"/></div>

</div>