<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Compare Admin Form</title>
</head>
<body>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <form:form method="POST" action="/price/api/product/admin/add" modelAttribute="product">
         <table>
            <tr>
                <div class="form-group">
                    <form:label path="name">Name</form:label>
                    <td><form:input path="name" lass="form-control"/></td>
                </div>
            </tr>
            <tr>
                <td><input type="submit" value="Submit" class="btn btn-primary" /></td>
            </tr>
        </table>
    </form:form>
</body>
</html>