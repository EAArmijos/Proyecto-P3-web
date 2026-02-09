package com.biblioteca.controllers;

import com.biblioteca.daos.LibroDAO;
import com.biblioteca.daos.PrestamoDAO;
import com.biblioteca.models.Prestamo;
import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PrestamoServlet", urlPatterns = {"/prestamos"})
public class PrestamoServlet extends HttpServlet {
    
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final LibroDAO libroDAO = new LibroDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        accion = (accion == null) ? "listar" : accion;
        
        switch (accion) {
            case "nuevo":
                mostrarFormulario(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                eliminarPrestamo(request, response);
                break;
            case "devolver":
                mostrarFormularioDevolucion(request, response);
                break;
            case "activos":
                listarPrestamosActivos(request, response);
                break;
            case "listar":
            default:
                listarPrestamos(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "guardar":
                guardarPrestamo(request, response);
                break;
            case "actualizar":
                actualizarPrestamo(request, response);
                break;
            case "devolver":
                registrarDevolucion(request, response);
                break;
        }
    }
    
    private void listarPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("prestamos", prestamoDAO.listar());
        request.getRequestDispatcher("/WEB-INF/prestamos/listar.jsp").forward(request, response);
    }
    
    private void listarPrestamosActivos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("prestamos", prestamoDAO.listarActivos());
        request.setAttribute("soloActivos", true);
        request.getRequestDispatcher("/WEB-INF/prestamos/listar.jsp").forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("librosDisponibles", libroDAO.listarDisponibles());
        request.getRequestDispatcher("/WEB-INF/prestamos/formulario.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Prestamo prestamo = prestamoDAO.obtenerPorId(id);
            
            if (prestamo != null) {
                request.setAttribute("prestamo", prestamo);
                request.setAttribute("esEdicion", true);
                request.getRequestDispatcher("/WEB-INF/prestamos/formulario.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=Prestamo+no+encontrado");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/prestamos?error=ID+inválido");
        }
    }
    
    private void mostrarFormularioDevolucion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Prestamo prestamo = prestamoDAO.obtenerPorId(id);
            
            if (prestamo != null) {
                request.setAttribute("prestamo", prestamo);
                request.getRequestDispatcher("/WEB-INF/prestamos/devolucion.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=Prestamo+no+encontrado");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/prestamos?error=ID+inválido");
        }
    }
    
    private void guardarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Prestamo prestamo = new Prestamo();
            
            // Validar ID del libro
            String idLibroStr = request.getParameter("id_libro");
            if (idLibroStr == null || idLibroStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=Libro+requerido");
                return;
            }
            prestamo.setIdLibro(Integer.parseInt(idLibroStr.trim()));
            
            // Validar nombre de usuario
            String nombreUsuario = request.getParameter("nombre_usuario");
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=Nombre+de+usuario+requerido");
                return;
            }
            prestamo.setNombreUsuario(nombreUsuario.trim());
            
            // Validar email de usuario
            String emailUsuario = request.getParameter("email_usuario");
            if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=Email+requerido");
                return;
            }
            prestamo.setEmailUsuario(emailUsuario.trim());
            
            prestamo.setFechaPrestamo(new Date(System.currentTimeMillis()));
            
            // Calcular fecha de devolución (14 días después)
            long tiempo = System.currentTimeMillis() + (14L * 24L * 60L * 60L * 1000L);
            prestamo.setFechaDevolucionEsperada(new Date(tiempo));
            prestamo.setEstado("activo");
            prestamo.setMulta(0.0);
            
            boolean success = prestamoDAO.insertar(prestamo);
            String mensaje = success ? "exito=Prestamo+registrado+correctamente" : "error=Error+al+registrar+prestamo";
            response.sendRedirect(request.getContextPath() + "/prestamos?" + mensaje);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/prestamos?error=Formato+numérico+inválido");
        }
    }
    
    private void actualizarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Prestamo prestamo = new Prestamo();
            
            // Validar ID del préstamo
            String idPrestamoStr = request.getParameter("id_prestamo");
            if (idPrestamoStr == null || idPrestamoStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=ID+de+préstamo+inválido");
                return;
            }
            prestamo.setIdPrestamo(Integer.parseInt(idPrestamoStr.trim()));
            
            // Validar ID del libro
            String idLibroStr = request.getParameter("id_libro");
            if (idLibroStr == null || idLibroStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=Libro+requerido");
                return;
            }
            prestamo.setIdLibro(Integer.parseInt(idLibroStr.trim()));
            
            prestamo.setNombreUsuario(request.getParameter("nombre_usuario"));
            prestamo.setEmailUsuario(request.getParameter("email_usuario"));
            
            String fechaPrestamoStr = request.getParameter("fecha_prestamo");
            if (fechaPrestamoStr != null && !fechaPrestamoStr.trim().isEmpty()) {
                prestamo.setFechaPrestamo(Date.valueOf(fechaPrestamoStr.trim()));
            }
            
            String fechaDevolucionEsperadaStr = request.getParameter("fecha_devolucion_esperada");
            if (fechaDevolucionEsperadaStr != null && !fechaDevolucionEsperadaStr.trim().isEmpty()) {
                prestamo.setFechaDevolucionEsperada(Date.valueOf(fechaDevolucionEsperadaStr.trim()));
            }
            
            prestamo.setEstado(request.getParameter("estado"));
            
            String multaStr = request.getParameter("multa");
            if (multaStr != null && !multaStr.trim().isEmpty()) {
                prestamo.setMulta(Double.parseDouble(multaStr.trim()));
            } else {
                prestamo.setMulta(0.0);
            }
            
            boolean success = prestamoDAO.actualizar(prestamo);
            String mensaje = success ? "exito=Prestamo+actualizado+correctamente" : "error=Error+al+actualizar+prestamo";
            response.sendRedirect(request.getContextPath() + "/prestamos?" + mensaje);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/prestamos?error=Formato+numérico+inválido");
        }
    }
    
    private void eliminarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = prestamoDAO.eliminar(id);
            String mensaje = success ? "exito=Prestamo+eliminado+correctamente" : "error=Error+al+eliminar+prestamo";
            response.sendRedirect(request.getContextPath() + "/prestamos?" + mensaje);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/prestamos?error=ID+inválido");
        }
    }
    
    private void registrarDevolucion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String idPrestamoStr = request.getParameter("id_prestamo");
            if (idPrestamoStr == null || idPrestamoStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/prestamos?error=ID+de+préstamo+inválido");
                return;
            }
            
            int idPrestamo = Integer.parseInt(idPrestamoStr.trim());
            Date fechaDevolucion = new Date(System.currentTimeMillis());
            
            String multaStr = request.getParameter("multa");
            double multa = 0.0;
            if (multaStr != null && !multaStr.trim().isEmpty()) {
                multa = Double.parseDouble(multaStr.trim());
            }
            
            boolean success = prestamoDAO.registrarDevolucion(idPrestamo, fechaDevolucion, multa);
            String mensaje = success ? "exito=Devolucion+registrada+correctamente" : "error=Error+al+registrar+devolucion";
            response.sendRedirect(request.getContextPath() + "/prestamos?" + mensaje);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/prestamos?error=Formato+numérico+inválido");
        }
    }
}