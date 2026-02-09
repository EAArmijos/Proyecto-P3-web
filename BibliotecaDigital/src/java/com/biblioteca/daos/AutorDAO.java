/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.daos;

import com.biblioteca.models.Autor;
import com.biblioteca.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Enzo Armijos
 */

public class AutorDAO implements GenericDAO<Autor> {
    
    @Override
    public List<Autor> listar() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autores ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                autores.add(new Autor(
                    rs.getInt("idAutor"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad"),
                    rs.getDate("fechaNacimiento"),
                    rs.getString("biografia")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }
    
    @Override
    public Autor obtenerPorId(int id) {
        Autor autor = null;
        String sql = "SELECT * FROM autores WHERE idAutor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    autor = new Autor(
                        rs.getInt("idAutor"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getDate("fechaNacimiento"),
                        rs.getString("biografia")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autor;
    }
    
    @Override
    public boolean insertar(Autor autor) {
        String sql = "INSERT INTO autores (nombre, nacionalidad, fechaNacimiento, biografia) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getNacionalidad());
            stmt.setDate(3, autor.getFechaNacimiento());
            stmt.setString(4, autor.getBiografia());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean actualizar(Autor autor) {
        String sql = "UPDATE autores SET nombre = ?, nacionalidad = ?, fechaNacimiento = ?, biografia = ? WHERE idAutor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getNacionalidad());
            stmt.setDate(3, autor.getFechaNacimiento());
            stmt.setString(4, autor.getBiografia());
            stmt.setInt(5, autor.getIdAutor());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM autores WHERE idAutor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}