package com.biblioteca.daos;

import com.biblioteca.models.Libro;
import com.biblioteca.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enzo Armijos
 */
public class LibroDAO implements GenericDAO<Libro> {
    
    @Override
    public List<Libro> listar() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, a.nombre as nombre_autor FROM libros l "
                   + "LEFT JOIN autores a ON l.idAutor = a.idAutor "
                   + "ORDER BY l.titulo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("idLibro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getInt("anioPublicacion"),
                    rs.getInt("idAutor"),
                    rs.getBoolean("disponible"),
                    rs.getString("genero"),
                    rs.getInt("paginas")
                );
                libro.setNombreAutor(rs.getString("nombre_autor"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
    
    @Override
    public Libro obtenerPorId(int id) {
        Libro libro = null;
        String sql = "SELECT l.*, a.nombre as nombre_autor FROM libros l "
                   + "LEFT JOIN autores a ON l.idAutor = a.idAutor "
                   + "WHERE l.idLibro = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    libro = new Libro(
                        rs.getInt("idLibro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getInt("anioPublicacion"),
                        rs.getInt("idAutor"),
                        rs.getBoolean("disponible"),
                        rs.getString("genero"),
                        rs.getInt("paginas")
                    );
                    libro.setNombreAutor(rs.getString("nombre_autor"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libro;
    }
    
    @Override
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, isbn, anioPublicacion, idAutor, genero, paginas, disponible) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getIsbn());
            stmt.setInt(3, libro.getAnioPublicacion());
            stmt.setInt(4, libro.getIdAutor());
            stmt.setString(5, libro.getGenero());
            stmt.setInt(6, libro.getPaginas());
            stmt.setBoolean(7, libro.isDisponible());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, isbn = ?, anioPublicacion = ?, "
                   + "idAutor = ?, genero = ?, paginas = ?, disponible = ? WHERE idLibro = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getIsbn());
            stmt.setInt(3, libro.getAnioPublicacion());
            stmt.setInt(4, libro.getIdAutor());
            stmt.setString(5, libro.getGenero());
            stmt.setInt(6, libro.getPaginas());
            stmt.setBoolean(7, libro.isDisponible());
            stmt.setInt(8, libro.getIdLibro());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE idLibro = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Método adicional para cambiar disponibilidad
    public boolean cambiarDisponibilidad(int idLibro, boolean disponible) {
        String sql = "UPDATE libros SET disponible = ? WHERE idLibro = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, disponible);
            stmt.setInt(2, idLibro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Obtener libros disponibles
    public List<Libro> listarDisponibles() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, a.nombre as nombre_autor FROM libros l "
                   + "LEFT JOIN autores a ON l.idAutor = a.idAutor "
                   + "WHERE l.disponible = true ORDER BY l.titulo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("idLibro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getInt("anioPublicacion"),
                    rs.getInt("idAutor"),
                    true,
                    rs.getString("genero"),
                    rs.getInt("paginas")
                );
                libro.setNombreAutor(rs.getString("nombre_autor"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}