<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <style>
        @import "${pageContext.request.contextPath}/resources/css/general.css";
    </style>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<div>
    <div align="center" style="padding-top: 10px">
        <table class="list">
            <tr>
                <td width="35%">Время</td>
                <td width="35%">Описание</td>
                <td width="18%">Калории</td>
                <td>Правка</td>
                <td>Удалить</td>
            </tr>
            <c:forEach items="${mealList}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMealWithExceed" scope="page"/>
                <fmt:parseDate value="${meal.dateTime}" var="parseDate" type="date" pattern="yyyy-MM-dd'T'mm:ss"/>
                <fmt:formatDate value="${parseDate}" var="date" pattern="yyyy-MM-dd mm:ss" type="date"/>
                <c:choose>
                    <c:when test="${meal.exceed == true}">
                        <tr style="color: darkred;">
                    </c:when>
                    <c:otherwise>
                        <tr>
                    </c:otherwise>
                </c:choose>
                <td>${date}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="<c:url value='/edit?id=${user.id}' />"><img
                        src="${pageContext.request.contextPath}/resources/images/user_edit.png"
                        width="20px" height="20px"/></a></td>
                <td><a href="<c:url value='/delete?id=${user.id}' />"><img
                        src="${pageContext.request.contextPath}/resources/images/user_remove.png"
                        width="20px" height="20px"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
