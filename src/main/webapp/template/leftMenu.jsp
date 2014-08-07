<%--
  Created by IntelliJ IDEA.
  User: ruki
  Date: 09.07.2014
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
<ul>
    <c:forEach var="category" items="${categories}">
        <c:set var="categories" value="${category.children}" scope="request"/>
        <li>
            <c:out value="${category.categoryName}" escapeXml="true"/>

            <c:if test="${fn:length(category.children) > 0}">
                <jsp:include page="leftMenu.jsp"/>
            </c:if>
        </li>
    </c:forEach>
</ul>

--%>


    <c:forEach var="category" items="${categories}">

        <c:set var="categories" value="${category.children}" scope="request"/>

        <c:choose>
            <c:when test="${fn:length(category.children) > 0}">

                <p class="menu_head">
                   <c:out value="${category.categoryName}"/>
                </p>

                <div class="menu_body">
                    <a href="#">
                        <jsp:include page="leftMenu.jsp"/>
                    </a>
                </div>

            </c:when>

            <c:otherwise>
                <p class="menu_head">
                    <a href="?category=<c:out value="${category.categoryID}" escapeXml="true"/>"/>  <c:out value="${category.categoryName}"/></a>
                </p>
            </c:otherwise>
        </c:choose>

    </c:forEach>