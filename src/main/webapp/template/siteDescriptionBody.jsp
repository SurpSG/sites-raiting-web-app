<%--
  Created by IntelliJ IDEA.
  User: ruki
  Date: 11.06.2014
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:forEach var="site" items="${sites}">
<div class="content_block_text">

    <div class="content_block_pic">${site.pictureURL}</div>

    <div class="head_text"><c:out value="${site.name}"/>
    </div>

    <a href="${site.url}"><div class="go_button">перейти</div></a>

    <br>

    <div class="border-wrap">
        <div id="rating_<c:out value="${site.id}" />">
            <input type="hidden" class="val" value="<c:out value="${site.rating}"/>"/>
            <input type="hidden" class="votes" value="2"/>
            <input type="hidden" class="vote-id" value="<c:out value="${site.id}"/>"/>
        </div>
    </div>

    <div class="info_block">
        Рейтинг: <c:out value="${site.rating}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Відвідуваність:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Яндекс:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Гугл:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    <br>

    <div class="content_text"><c:out value="${site.description}"/></div>

</div>

</c:forEach>