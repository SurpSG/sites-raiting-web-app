<%--
  Created by IntelliJ IDEA.
  User: ruki
  Date: 11.06.2014
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <c:forEach var="site" items="${sites}">

        <div class="content_block">
            <div class="content_block_pic">${site.pictureURL}</div>

            <div class="content_block_text">

                <a href="/site?id=<c:out value="${site.id}" />">
                <div class="head_text"><c:out value="${site.name}" /></div>
                </a>

                <div class="wrap">
                    <div id="rating_<c:out value="${site.id}" />">
                        <input type="hidden" class="val" value="<c:out value="${site.rating}"/>"/>
                        <input type="hidden" class="votes" value="2"/>
                        <input type="hidden" class="vote-id" value="voteID"/>
                    </div>
                </div>

                <div class="content_text"><c:out value="${site.shortDescription}"/>...</div>

            </div>

            <div class="info_block">
                Відвідуваність:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Яндекс:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Гугл:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </div>

        </div>


    </c:forEach>
