<%--
  Created by IntelliJ IDEA.
  User: ruki
  Date: 11.06.2014
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <script type="text/javascript">

        <c:forEach var="site" items="${sites}">

        $(function(){
            $('<c:out value="#rating_${site.id}"/>').rating({
                fx: 'full',
                image: 'template/images/stars.png',
                loader: 'template/images/ajax-loader.gif',
                width: 32,
                url: 'rating.php'
            });

        })
        </c:forEach>

    </script>