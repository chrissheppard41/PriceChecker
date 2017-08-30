<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Compare Admin Form</title>
</head>
<body>
    Hello ${id}

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <form:form method="POST" action="/admin/{id}" modelAttribute="product">
                 <table>
                    <tr>
                        <td><form:label path="name">Name</form:label></td>
                        <td><form:input path="name"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit"/></td>
                    </tr>
                </table>
            </form:form>
</body>
</html>