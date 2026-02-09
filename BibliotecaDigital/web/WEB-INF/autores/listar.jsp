<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Lista de Autores</title>
</head>
<body>
    <div class="container mt-4">
        <h2>Autores</h2>
        
        <c:if test="${param.exito != null}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${param.exito}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${param.error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <a class="btn btn-success mb-3"
           href="${pageContext.request.contextPath}/autores?accion=nuevo">
           Nuevo Autor
        </a>
        
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Nacionalidad</th>
                    <th>Fecha Nacimiento</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${autores}">
                    <tr>
                        <td>${a.idAutor}</td>
                        <td>${a.nombre}</td>
                        <td>${a.nacionalidad}</td>
                        <td>${a.fechaNacimiento}</td>
                        <td>
                            <a class="btn btn-sm btn-warning"
                               href="${pageContext.request.contextPath}/autores?accion=editar&id=${a.idAutor}">
                               Editar
                            </a>
                            <a class="btn btn-sm btn-danger"
                               href="${pageContext.request.contextPath}/autores?accion=eliminar&id=${a.idAutor}"
                               onclick="return confirm('¿Está seguro de eliminar este autor?')">
                               Eliminar
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Volver al Inicio</a>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>