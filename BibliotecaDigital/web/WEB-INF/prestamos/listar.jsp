<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container mt-4">
<h2>Préstamos</h2>

<a class="btn btn-success mb-3"
 href="prestamos?accion=nuevo">Nuevo Préstamo</a>

<table class="table table-bordered">
<tr>
<th>ID</th><th>Usuario</th><th>Email</th><th>Estado</th><th>Acciones</th>
</tr>

<c:forEach var="p" items="${prestamos}">
<tr>
<td>${p.idPrestamo}</td>
<td>${p.nombreUsuario}</td>
<td>${p.emailUsuario}</td>
<td>${p.estado}</td>
<td>
<a class="btn btn-warning btn-sm"
 href="prestamos?accion=editar&id=${p.idPrestamo}">Editar</a>

<a class="btn btn-danger btn-sm"
 href="prestamos?accion=eliminar&id=${p.idPrestamo}">Eliminar</a>
</td>
</tr>
</c:forEach>
</table>

<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Volver al Inicio</a>
</div>
