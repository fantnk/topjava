<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt" %>

<html>
<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><a href="index.html"><fmt:message key="navi.home"/></a></h2>
    <h3><fmt_rt:message key="${meal.isNew() ? 'meals.create' : 'meals.update'}"/></h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMeal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><fmt:message key="table.date"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="table.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="table.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit"><fmt:message key="btn.save"/></button>
        <button onclick="window.history.back()"><fmt:message key="btn.cancel"/></button>
    </form>
</section>
</body>
</html>
