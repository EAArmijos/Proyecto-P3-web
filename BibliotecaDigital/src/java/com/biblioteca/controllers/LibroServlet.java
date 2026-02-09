package com.biblioteca.controllers;

import com.biblioteca.daos.AutorDAO;
import com.biblioteca.daos.LibroDAO;
import com.biblioteca.models.Libro;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LibroServlet", urlPatterns = {"/libros"})
public class LibroServlet extends HttpServlet {
    
    private final LibroDAO libroDAO = new LibroDAO();
    private final AutorDAO autorDAO = new AutorDAO();
    
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
                eliminarLibro(request, response);
                break;
            case "cambiarDisponibilidad":
                cambiarDisponibilidad(request, response);
                break;
            case "listar":
            default:
                listarLibros(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "guardar":
                guardarLibro(request, response);
                break;
            case "actualizar":
                actualizarLibro(request, response);
                break;
        }
    }
    
    private void listarLibros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("libros", libroDAO.listar());
        request.getRequestDispatcher("/WEB-INF/libros/listar.jsp").forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("autores", autorDAO.listar());
        request.getRequestDispatcher("/WEB-INF/libros/formulario.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Libro libro = libroDAO.obtenerPorId(id);
            
            if (libro != null) {
                request.setAttribute("libro", libro);
                request.setAttribute("autores", autorDAO.listar());
                request.setAttribute("esEdicion", true);
                request.getRequestDispatcher("/WEB-INF/libros/formulario.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/libros?error=Libro+no+encontrado");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/libros?error=ID+inválido");
        }
    }
    
    private void guardarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Libro libro = new Libro();
            
            // Validar título
            String titulo = request.getParameter("titulo");
            if (titulo == null || titulo.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=Título+requerido");
                return;
            }
            libro.setTitulo(titulo.trim());
            
            // Validar ISBN
            String isbn = request.getParameter("isbn");
            if (isbn == null || isbn.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=ISBN+requerido");
                return;
            }
            libro.setIsbn(isbn.trim());
            
            // Validar año de publicación
            String anioStr = request.getParameter("anio_publicacion");
            if (anioStr == null || anioStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=Año+de+publicación+requerido");
                return;
            }
            libro.setAnioPublicacion(Integer.parseInt(anioStr.trim()));
            
            // Validar autor
            String idAutorStr = request.getParameter("id_autor");
            if (idAutorStr == null || idAutorStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=Autor+requerido");
                return;
            }
            libro.setIdAutor(Integer.parseInt(idAutorStr.trim()));
            
            // Género (opcional)
            String genero = request.getParameter("genero");
            libro.setGenero((genero != null && !genero.trim().isEmpty()) ? genero.trim() : "");
            
            // Validar páginas (opcional)
            String paginasStr = request.getParameter("paginas");
            if (paginasStr != null && !paginasStr.trim().isEmpty()) {
                libro.setPaginas(Integer.parseInt(paginasStr.trim()));
            } else {
                libro.setPaginas(0);
            }
            
            // Disponibilidad - por defecto true al crear
            String disponibleStr = request.getParameter("disponible");
            libro.setDisponible(disponibleStr != null && disponibleStr.equals("true"));
            
            boolean success = libroDAO.insertar(libro);
            String mensaje = success ? "exito=Libro+creado+correctamente" : "error=Error+al+crear+libro";
            response.sendRedirect(request.getContextPath() + "/libros?" + mensaje);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/libros?error=Formato+numérico+inválido");
        }
    }
    
    private void actualizarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Libro libro = new Libro();
            
            // Validar ID del libro
            String idLibroStr = request.getParameter("id_libro");
            if (idLibroStr == null || idLibroStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=ID+de+libro+inválido");
                return;
            }
            libro.setIdLibro(Integer.parseInt(idLibroStr.trim()));
            
            // Validar título
            String titulo = request.getParameter("titulo");
            if (titulo == null || titulo.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=Título+requerido");
                return;
            }
            libro.setTitulo(titulo.trim());
            
            // Validar ISBN
            String isbn = request.getParameter("isbn");
            if (isbn == null || isbn.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=ISBN+requerido");
                return;
            }
            libro.setIsbn(isbn.trim());
            
            // Validar año de publicación
            String anioStr = request.getParameter("anio_publicacion");
            if (anioStr == null || anioStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=Año+de+publicación+requerido");
                return;
            }
            libro.setAnioPublicacion(Integer.parseInt(anioStr.trim()));
            
            // Validar autor
            String idAutorStr = request.getParameter("id_autor");
            if (idAutorStr == null || idAutorStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/libros?error=Autor+requerido");
                return;
            }
            libro.setIdAutor(Integer.parseInt(idAutorStr.trim()));
            
            // Género (opcional)
            String genero = request.getParameter("genero");
            libro.setGenero((genero != null && !genero.trim().isEmpty()) ? genero.trim() : "");
            
            // Validar páginas (opcional)
            String paginasStr = request.getParameter("paginas");
            if (paginasStr != null && !paginasStr.trim().isEmpty()) {
                libro.setPaginas(Integer.parseInt(paginasStr.trim()));
            } else {
                libro.setPaginas(0);
            }
            
            // Disponibilidad
            String disponibleStr = request.getParameter("disponible");
            libro.setDisponible(disponibleStr != null && disponibleStr.equals("true"));
            
            boolean success = libroDAO.actualizar(libro);
            String mensaje = success ? "exito=Libro+actualizado+correctamente" : "error=Error+al+actualizar+libro";
            response.sendRedirect(request.getContextPath() + "/libros?" + mensaje);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/libros?error=Formato+numérico+inválido");
        }
    }
    
    private void eliminarLibro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = libroDAO.eliminar(id);
            String mensaje = success ? "exito=Libro+eliminado+correctamente" : "error=Error+al+eliminar+libro";
            response.sendRedirect(request.getContextPath() + "/libros?" + mensaje);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/libros?error=ID+inválido");
        }
    }
    
    private void cambiarDisponibilidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean disponible = Boolean.parseBoolean(request.getParameter("disponible"));
            
            boolean success = libroDAO.cambiarDisponibilidad(id, disponible);
            String mensaje = success ? "exito=Disponibilidad+actualizada" : "error=Error+al+cambiar+disponibilidad";
            response.sendRedirect(request.getContextPath() + "/libros?" + mensaje);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/libros?error=ID+inválido");
        }
    }
}