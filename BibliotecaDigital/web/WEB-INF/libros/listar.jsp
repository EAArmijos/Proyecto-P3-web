<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Lista de Libros</title>
</head>
<body>
    <div class="container mt-4">
        <h2>Libros</h2>
        
        <!-- Mensajes -->
        <c:if test="${param.exito != null}">
            <div class="alert alert-success alert-dismissible fade show">
                ${param.exito}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger alert-dismissible fade show">
                ${param.error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <a class="btn btn-success mb-3" 
           href="${pageContext.request.contextPath}/libros?accion=nuevo">
            Nuevo Libro
        </a>
        
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>ISBN</th>
                    <th>Autor</th>
                    <th>Año</th>
                    <th>Género</th>
                    <th>Disponibilidad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${libros}">
                    <tr>
                        <td>${l.idLibro}</td>
                        <td>${l.titulo}</td>
                        <td>${l.isbn}</td>
                        <td>${l.nombreAutor}</td>
                        <td>${l.anioPublicacion}</td>
                        <td>${l.genero}</td>
                        <td>
                            <c:choose>
                                <c:when test="${l.disponible}">
                                    <span class="badge bg-success">Disponible</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">No disponible</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-warning" 
                               href="${pageContext.request.contextPath}/libros?accion=editar&id=${l.idLibro}">
                                Editar
                            </a>
                            <a class="btn btn-sm btn-danger" 
                               href="${pageContext.request.contextPath}/libros?accion=eliminar&id=${l.idLibro}"
                               onclick="return confirm('¿Está seguro de eliminar este libro?')">
                                Eliminar
                            </a>
                            <c:choose>
                                <c:when test="${l.disponible}">
                                    <a class="btn btn-sm btn-secondary" 
                                       href="${pageContext.request.contextPath}/libros?accion=cambiarDisponibilidad&id=${l.idLibro}&disponible=false"
                                       onclick="return confirm('¿Marcar como NO disponible?')">
                                        Marcar No Disponible
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-sm btn-success" 
                                       href="${pageContext.request.contextPath}/libros?accion=cambiarDisponibilidad&id=${l.idLibro}&disponible=true"
                                       onclick="return confirm('¿Marcar como disponible?')">
                                        Marcar Disponible
                                    </a>
                                </c:otherwise>
                            </c:choose>
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