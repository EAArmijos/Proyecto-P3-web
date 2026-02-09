package com.biblioteca.controllers;

import com.biblioteca.daos.AutorDAO;
import com.biblioteca.models.Autor;
import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AutorServlet", urlPatterns = {"/autores"})
public class AutorServlet extends HttpServlet {
    
    private final AutorDAO autorDAO = new AutorDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        accion = (accion == null) ? "listar" : accion;
        
        switch (accion) {
            case "nuevo":
                mostrarFormulario(request, response, false);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                eliminarAutor(request, response);
                break;
            case "listar":
            default:
                listarAutores(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "guardar":
                guardarAutor(request, response);
                break;
            case "actualizar":
                actualizarAutor(request, response);
                break;
        }
    }
    
    private void listarAutores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("autores", autorDAO.listar());
        request.getRequestDispatcher("/WEB-INF/autores/listar.jsp").forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, boolean esEdicion)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/autores/formulario.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Autor autor = autorDAO.obtenerPorId(id);
        
        if (autor != null) {
            request.setAttribute("autor", autor);
            request.setAttribute("esEdicion", true);
            request.getRequestDispatcher("/WEB-INF/autores/formulario.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/autores?error=Autor+no+encontrado");
        }
    }
    
    private void guardarAutor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Autor autor = new Autor();
        autor.setNombre(request.getParameter("nombre"));
        autor.setNacionalidad(request.getParameter("nacionalidad"));
        autor.setBiografia(request.getParameter("biografia"));
        
        String fechaStr = request.getParameter("fecha_nacimiento");
        if (fechaStr != null && !fechaStr.isEmpty()) {
            autor.setFechaNacimiento(Date.valueOf(fechaStr));
        }
       
        boolean success = autorDAO.insertar(autor);
        String mensaje = success ? "exito=Autor+creado+correctamente" : "error=Error+al+crear+autor";
        response.sendRedirect(request.getContextPath() + "/autores?" + mensaje);
    }
    
    private void actualizarAutor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Autor autor = new Autor();
        autor.setIdAutor(Integer.parseInt(request.getParameter("id_autor")));
        autor.setNombre(request.getParameter("nombre"));
        autor.setNacionalidad(request.getParameter("nacionalidad"));
        autor.setBiografia(request.getParameter("biografia"));
        
        String fechaStr = request.getParameter("fecha_nacimiento");
        if (fechaStr != null && !fechaStr.isEmpty()) {
            autor.setFechaNacimiento(Date.valueOf(fechaStr));
        }
        
        boolean success = autorDAO.actualizar(autor);
        String mensaje = success ? "exito=Autor+actualizado+correctamente" : "error=Error+al+actualizar+autor";
        response.sendRedirect(request.getContextPath() + "/autores?" + mensaje);
    }
    
    private void eliminarAutor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = autorDAO.eliminar(id);
        String mensaje = success ? "exito=Autor+eliminado+correctamente" : "error=Error+al+eliminar+autor";
        response.sendRedirect(request.getContextPath() + "/autores?" + mensaje);
    }
}