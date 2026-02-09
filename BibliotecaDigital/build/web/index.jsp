<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Biblioteca Digital</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .card {
                transition: transform 0.2s;
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <div class="text-center mb-5">
                <h1>Biblioteca Digital - Sistema CRUD</h1>
                <p class="lead">Proyecto MVC con Jakarta, Servlets, JSP y MySQL</p>
            </div>
            
            <div class="row mt-4 g-4">
                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body text-center">
                            <h5 class="card-title">Autores</h5>
                            <p class="card-text">Gestiona la información de los autores</p>
                            <a href="${pageContext.request.contextPath}/autores" 
                               class="btn btn-primary">Ver Autores</a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body text-center">
                            <h5 class="card-title">Libros</h5>
                            <p class="card-text">Administra el catálogo de libros</p>
                            <a href="${pageContext.request.contextPath}/libros" 
                               class="btn btn-success">Ver Libros</a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body text-center">
                            <h5 class="card-title">Préstamos</h5>
                            <p class="card-text">Controla los préstamos de libros</p>
                            <a href="${pageContext.request.contextPath}/prestamos" 
                               class="btn btn-warning">Ver Préstamos</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>