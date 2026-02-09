<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container mt-4">
<h2>Registrar Devoluci�n</h2>

<form method="post" action="prestamos">
<input type="hidden" name="accion" value="devolver">
<input type="hidden" name="id_prestamo" value="${prestamo.idPrestamo}">

<input name="multa" class="form-control mb-2"
 placeholder="Multa">

<button class="btn btn-success">Registrar</button>
<a href="${pageContext.request.contextPath}/prestamos" class="btn btn-secondary">Cancelar</a>
<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Volver al Inicio</a>
</form>
</div>
