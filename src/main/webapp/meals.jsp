<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<hr>
<h3><a href="meals?action=add">Add meal</a></h3>
<br/>
<table border="1" style="border-color: black" cellpadding="10" cellspacing="0">
    <tr style="font-weight: bold" align="center">
        <td>ID</td>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td colspan="2">Actions</td>
    </tr>
    <c:forEach items="${meals}" var="mealTo">
        <c:choose>
            <c:when test="${mealTo.excess}">
                <tr style="color: red">
            </c:when>
            <c:otherwise>
                <tr style="color: green">
            </c:otherwise>
        </c:choose>
        <td>${mealTo.id}</td>
        <td>${TimeUtil.dateTimeFormat(mealTo.dateTime)}</td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td><a href="meals?id=${mealTo.id}&action=update">update</a></td>
        <td><a href="meals?id=${mealTo.id}&action=delete">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
