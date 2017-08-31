<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="/css/bootstrap.min.css" rel="stylesheet"/>

    <title>Compare Admin</title>
</head>
<body>
    <nav class="navbar navbar-light bg-faded">
        <div class="container">
            <ul class="nav navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="/price/api/compare/admin/">
                        <h5>Compare Stats</h5>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/price/api/product/admin/">
                        Products
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container m-t-2">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-12">
                        <h1>Product</h1>
                    </div>
                    <div class="col-md-12">
                        <a href="/price/api/product/admin/add" class="btn btn-success">Create new Product</a>


                        <table class="table table-striped">
                            <thead>
                                <tr class="row">
                                    <th class="col-6">Name</th>
                                    <th class="col-3">Modified</th>
                                    <th class="col-3">Options</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="posts" items="${posts}">
                                    <tr class="row">
                                        <td class="col-6">
                                            <c:out value="${posts.name}"/>
                                        </td>
                                        <td class="col-3">
                                            <c:out value="${posts.date}"/>
                                        </td>
                                        <td class="col-3">
                                            <a href="/price/api/product/admin/edit/${posts.id}" class="btn btn-primary"><i class="glyphicon glyphicon-edit"></i>Edit</a>
                                            <a href="/price/api/product/admin/delete/${posts.id}" class="btn btn-danger"><i class="glyphicon glyphicon-trash"></i>Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                          </tbody>
                        </table>


                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/bootstrap.min.js"></script>
</body>
</html>