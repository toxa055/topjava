<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${empty meals ? 'Create meal' : 'Edit meal'}</title>
</head>
<h2><a href="index.html">Home</a></h2>
<style>
    #descr {
        width: 400px;
    }
</style>
<body>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <h2>${empty meals ? 'Create meal:' : 'Edit meal:'}</h2>
    <input type="hidden" name="id" value="${meals.id}">
    <p>
        DateTime:
        <input type="datetime-local" name="dateTime"
               value="${meals.dateTime}"
               placeholder="YYYY-MM-DDTHH:mm"/>
    </p>
    <p>
        Description:
        <input type="text" name="description" id="descr" value="${meals.description}"/>
    </p>
    <p>
        Calories:
        <input type="number" name="calories" value="${meals.calories}"/>
    </p>
    <button type="submit">Save</button>
    <button type="reset" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
