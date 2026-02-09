<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>${esEdicion ? "Editar" : "Nuevo"} Autor</title>
</head>
<body>
    <div class="container mt-4">
        <h2>${esEdicion ? "Editar" : "Nuevo"} Autor</h2>
        
        <form method="post" action="${pageContext.request.contextPath}/autores">
            <input type="hidden" name="accion"
                   value="${esEdicion ? 'actualizar' : 'guardar'}">
            
            <c:if test="${esEdicion}">
                <input type="hidden" name="id_autor" value="${autor.idAutor}">
            </c:if>
            
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" 
                       placeholder="Nombre" value="${autor.nombre}" required>
            </div>
            
            <div class="mb-3">
                <label for="nacionalidad" class="form-label">Nacionalidad</label>
                <input type="text" class="form-control" id="nacionalidad" name="nacionalidad" 
                       placeholder="Nacionalidad" value="${autor.nacionalidad}" required>
            </div>
            
            <div class="mb-3">
                <label for="biografia" class="form-label">Biografía</label>
                <textarea class="form-control" id="biografia" name="biografia" 
                          rows="4" placeholder="Biografía">${autor.biografia}</textarea>
            </div>
            
            <div class="mb-3">
                <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                <input type="date" class="form-control" id="fecha_nacimiento" 
                       name="fecha_nacimiento" value="${autor.fechaNacimiento}">
            </div>
            
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="${pageContext.request.contextPath}/autores" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>