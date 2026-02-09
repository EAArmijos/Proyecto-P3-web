<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>${esEdicion ? "Editar" : "Nuevo"} Libro</title>
</head>
<body>
    <div class="container mt-4">
        <h2>${esEdicion ? "Editar" : "Nuevo"} Libro</h2>
        
        <form method="post" action="${pageContext.request.contextPath}/libros">
            <input type="hidden" name="accion" 
                   value="${esEdicion ? 'actualizar' : 'guardar'}">
            
            <c:if test="${esEdicion}">
                <input type="hidden" name="id_libro" value="${libro.idLibro}">
            </c:if>
            
            <div class="mb-3">
                <label for="titulo" class="form-label">Título</label>
                <input type="text" name="titulo" id="titulo" 
                       class="form-control" placeholder="Título del libro" 
                       value="${libro.titulo}" required>
            </div>
            
            <div class="mb-3">
                <label for="isbn" class="form-label">ISBN</label>
                <input type="text" name="isbn" id="isbn" 
                       class="form-control" placeholder="ISBN" 
                       value="${libro.isbn}" required>
            </div>
            
            <div class="mb-3">
                <label for="id_autor" class="form-label">Autor</label>
                <select name="id_autor" id="id_autor" class="form-control" required>
                    <option value="">-- Seleccione un autor --</option>
                    <c:forEach var="a" items="${autores}">
                        <option value="${a.idAutor}" 
                                ${esEdicion && libro.idAutor == a.idAutor ? 'selected' : ''}>
                            ${a.nombre}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="anio_publicacion" class="form-label">Año de Publicación</label>
                <input type="number" name="anio_publicacion" id="anio_publicacion" 
                       class="form-control" placeholder="2024" 
                       value="${libro.anioPublicacion}" min="1000" max="2100" required>
            </div>
            
            <div class="mb-3">
                <label for="genero" class="form-label">Género</label>
                <input type="text" name="genero" id="genero" 
                       class="form-control" placeholder="Ficción, Drama, etc." 
                       value="${libro.genero}">
            </div>
            
            <div class="mb-3">
                <label for="paginas" class="form-label">Páginas</label>
                <input type="number" name="paginas" id="paginas" 
                       class="form-control" placeholder="Número de páginas" 
                       value="${libro.paginas}" min="0">
            </div>
            
            <button type="submit" class="btn btn-primary">
                ${esEdicion ? 'Actualizar' : 'Guardar'} Libro
            </button>
            <a href="${pageContext.request.contextPath}/libros" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>