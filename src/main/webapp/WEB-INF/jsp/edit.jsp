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
    <form:form method="POST" action="/price/api/product/admin/edit/${product.id}" modelAttribute="product">
         <table>
            <tr class="form-group">
                    <td><form:label path="name">Name</form:label></td>
                    <td><form:input path="name" class="form-control"/></td>
            </tr>
            <tr>
                <td>
                    <form:input type="hidden" path="id" class="form-control"/>
                    <input type="submit" value="Submit" class="btn btn-primary" />
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>