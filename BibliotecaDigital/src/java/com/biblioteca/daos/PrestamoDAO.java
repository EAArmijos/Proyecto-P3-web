/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.daos;

import com.biblioteca.models.Prestamo;
import com.biblioteca.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Enzo Armijos
 */
public class PrestamoDAO implements GenericDAO<Prestamo> {
    
    @Override
    public List<Prestamo> listar() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo as titulo_libro FROM prestamos p "
                   + "LEFT JOIN libros l ON p.idLibro = l.idLibro "
                   + "ORDER BY p.fechaPrestamo DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("idPrestamo"));
                prestamo.setIdLibro(rs.getInt("idLibro"));
                prestamo.setNombreUsuario(rs.getString("nombreUsuario"));
                prestamo.setEmailUsuario(rs.getString("emailUsuario"));
                prestamo.setFechaPrestamo(rs.getDate("fechaPrestamo"));
                prestamo.setFechaDevolucion(rs.getDate("fechaDevolucion"));
                prestamo.setFechaDevolucionEsperada(rs.getDate("fechaDevolucionEsperada"));
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setMulta(rs.getDouble("multa"));
                prestamo.setTituloLibro(rs.getString("titulo_libro"));
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    
    @Override
    public Prestamo obtenerPorId(int id) {
        Prestamo prestamo = null;
        String sql = "SELECT p.*, l.titulo as titulo_libro FROM prestamos p "
                   + "LEFT JOIN libros l ON p.idLibro = l.idLibro "
                   + "WHERE p.idPrestamo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    prestamo = new Prestamo();
                    prestamo.setIdPrestamo(rs.getInt("idPrestamo"));
                    prestamo.setIdLibro(rs.getInt("idLibro"));
                    prestamo.setNombreUsuario(rs.getString("nombreUsuario"));
                    prestamo.setEmailUsuario(rs.getString("emailUsuario"));
                    prestamo.setFechaPrestamo(rs.getDate("fechaPrestamo"));
                    prestamo.setFechaDevolucion(rs.getDate("fechaDevolucion"));
                    prestamo.setFechaDevolucionEsperada(rs.getDate("fechaDevolucionEsperada"));
                    prestamo.setEstado(rs.getString("estado"));
                    prestamo.setMulta(rs.getDouble("multa"));
                    prestamo.setTituloLibro(rs.getString("titulo_libro"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamo;
    }
    
    @Override
    public boolean insertar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (idLibro, nombreUsuario, emailUsuario, "
                   + "fechaPrestamo, fechaDevolucionEsperada, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, prestamo.getIdLibro());
            stmt.setString(2, prestamo.getNombreUsuario());
            stmt.setString(3, prestamo.getEmailUsuario());
            stmt.setDate(4, prestamo.getFechaPrestamo());
            stmt.setDate(5, prestamo.getFechaDevolucionEsperada());
            stmt.setString(6, prestamo.getEstado());
            
            boolean result = stmt.executeUpdate() > 0;
            
            // Si se insertó el préstamo, marcar libro como no disponible
            if (result) {
                LibroDAO libroDAO = new LibroDAO();
                libroDAO.cambiarDisponibilidad(prestamo.getIdLibro(), false);
            }
            
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean actualizar(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET idLibro = ?, nombreUsuario = ?, emailUsuario = ?, "
                   + "fechaPrestamo = ?, fechaDevolucion = ?, fechaDevolucionEsperada = ?, "
                   + "estado = ?, multa = ? WHERE idPrestamo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, prestamo.getIdLibro());
            stmt.setString(2, prestamo.getNombreUsuario());
            stmt.setString(3, prestamo.getEmailUsuario());
            stmt.setDate(4, prestamo.getFechaPrestamo());
            stmt.setDate(5, prestamo.getFechaDevolucion());
            stmt.setDate(6, prestamo.getFechaDevolucionEsperada());
            stmt.setString(7, prestamo.getEstado());
            stmt.setDouble(8, prestamo.getMulta());
            stmt.setInt(9, prestamo.getIdPrestamo());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        // Primero obtener el préstamo para saber el libro
        Prestamo prestamo = obtenerPorId(id);
        if (prestamo == null) return false;
        
        String sql = "DELETE FROM prestamos WHERE idPrestamo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            boolean result = stmt.executeUpdate() > 0;
            
            // Si se eliminó el préstamo, marcar libro como disponible
            if (result) {
                LibroDAO libroDAO = new LibroDAO();
                libroDAO.cambiarDisponibilidad(prestamo.getIdLibro(), true);
            }
            
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para registrar devolución
    public boolean registrarDevolucion(int idPrestamo, Date fechaDevolucion, double multa) {
        String sql = "UPDATE prestamos SET fechaDevolucion = ?, estado = 'devuelto', multa = ? WHERE idPrestamo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, fechaDevolucion);
            stmt.setDouble(2, multa);
            stmt.setInt(3, idPrestamo);
            
            boolean result = stmt.executeUpdate() > 0;
            
            // Si se registró devolución, marcar libro como disponible
            if (result) {
                Prestamo prestamo = obtenerPorId(idPrestamo);
                if (prestamo != null) {
                    LibroDAO libroDAO = new LibroDAO();
                    libroDAO.cambiarDisponibilidad(prestamo.getIdLibro(), true);
                }
            }
            
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para obtener préstamos activos
    public List<Prestamo> listarActivos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo as titulo_libro FROM prestamos p "
                   + "LEFT JOIN libros l ON p.idLibro = l.idLibro "
                   + "WHERE p.estado = 'activo' ORDER BY p.fechaPrestamo DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("idPrestamo"));
                prestamo.setIdLibro(rs.getInt("idLibro"));
                prestamo.setNombreUsuario(rs.getString("nombreUsuario"));
                prestamo.setEmailUsuario(rs.getString("emailUsuario"));
                prestamo.setFechaPrestamo(rs.getDate("fechaPrestamo"));
                prestamo.setFechaDevolucionEsperada(rs.getDate("fechaDevolucionEsperada"));
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setTituloLibro(rs.getString("titulo_libro"));
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
}
