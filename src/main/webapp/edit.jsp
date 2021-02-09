<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${empty meal ? 'Create meal' : 'Edit meal'}</title>
</head>
<h2><a href="index.html">Home</a></h2>
<style>
    #descr {
        width: 400px;
    }
</style>
<body>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <h2>${empty meal ? 'Create meal:' : 'Edit meal:'}</h2>
    <input type="hidden" name="id" value="${meal.id}">
    <p>
        DateTime:
        <input type="datetime-local" name="dateTime"
               value="${meal.dateTime}"
               placeholder="YYYY-MM-DDTHH:mm"/>
    </p>
    <p>
        Description:
        <input type="text" name="description" id="descr" value="${meal.description}"/>
    </p>
    <p>
        Calories:
        <input type="number" name="calories" value="${meal.calories}"/>
    </p>
    <button type="submit">Save</button>
    <button type="reset" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
