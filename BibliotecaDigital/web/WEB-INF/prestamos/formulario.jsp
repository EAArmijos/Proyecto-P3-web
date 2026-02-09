<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>${esEdicion ? "Editar" : "Nuevo"} Préstamo</title>
</head>
<body>
    <div class="container mt-4">
        <h2>${esEdicion ? "Editar" : "Nuevo"} Préstamo</h2>
        
        <form method="post" action="${pageContext.request.contextPath}/prestamos">
            <input type="hidden" name="accion" 
                   value="${esEdicion ? 'actualizar' : 'guardar'}">
            
            <c:if test="${esEdicion}">
                <input type="hidden" name="id_prestamo" value="${prestamo.idPrestamo}">
            </c:if>
            
            <div class="mb-3">
                <label for="id_libro" class="form-label">Libro</label>
                <select name="id_libro" id="id_libro" class="form-control" required
                        ${esEdicion ? 'disabled' : ''}>
                    <option value="">-- Seleccione un libro --</option>
                    <c:forEach var="l" items="${librosDisponibles}">
                        <option value="${l.idLibro}" 
                                ${esEdicion && prestamo.idLibro == l.idLibro ? 'selected' : ''}>
                            ${l.titulo} - ${l.nombreAutor}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${esEdicion}">
                    <input type="hidden" name="id_libro" value="${prestamo.idLibro}">
                </c:if>
            </div>
            
            <div class="mb-3">
                <label for="nombre_usuario" class="form-label">Nombre del Usuario</label>
                <input type="text" name="nombre_usuario" id="nombre_usuario" 
                       class="form-control" placeholder="Nombre completo" 
                       value="${prestamo.nombreUsuario}" required>
            </div>
            
            <div class="mb-3">
                <label for="email_usuario" class="form-label">Email del Usuario</label>
                <input type="email" name="email_usuario" id="email_usuario" 
                       class="form-control" placeholder="ejemplo@email.com" 
                       value="${prestamo.emailUsuario}" required>
            </div>
            
            <c:if test="${esEdicion}">
                <div class="mb-3">
                    <label for="fecha_prestamo" class="form-label">Fecha de Préstamo</label>
                    <input type="date" name="fecha_prestamo" id="fecha_prestamo" 
                           class="form-control" value="${prestamo.fechaPrestamo}">
                </div>
                
                <div class="mb-3">
                    <label for="fecha_devolucion_esperada" class="form-label">Fecha de Devolución Esperada</label>
                    <input type="date" name="fecha_devolucion_esperada" id="fecha_devolucion_esperada" 
                           class="form-control" value="${prestamo.fechaDevolucionEsperada}">
                </div>
                
                <div class="mb-3">
                    <label for="estado" class="form-label">Estado</label>
                    <select name="estado" id="estado" class="form-control">
                        <option value="activo" ${prestamo.estado == 'activo' ? 'selected' : ''}>Activo</option>
                        <option value="devuelto" ${prestamo.estado == 'devuelto' ? 'selected' : ''}>Devuelto</option>
                        <option value="vencido" ${prestamo.estado == 'vencido' ? 'selected' : ''}>Vencido</option>
                    </select>
                </div>
                
                <div class="mb-3">
                    <label for="multa" class="form-label">Multa (€)</label>
                    <input type="number" step="0.01" name="multa" id="multa" 
                           class="form-control" value="${prestamo.multa}" min="0">
                </div>
            </c:if>
            
            <c:if test="${!esEdicion}">
                <div class="alert alert-info">
                    <strong>Nota:</strong> El préstamo será por 14 días a partir de hoy.
                </div>
            </c:if>
            
            <button type="submit" class="btn btn-primary">
                ${esEdicion ? 'Actualizar' : 'Guardar'} Préstamo
            </button>
            <a href="${pageContext.request.contextPath}/prestamos" class="btn btn-secondary">Cancelar</a>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Volver al Inicio</a>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>