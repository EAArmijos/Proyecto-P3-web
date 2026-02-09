package com.biblioteca.models;

public class Libro {
    private int idLibro;
    private String titulo;
    private String isbn;
    private int anioPublicacion;
    private int idAutor;
    private boolean disponible;
    private String genero;
    private int paginas;
    private String nombreAutor; // Para mostrar en las vistas
    
    // Constructor vacío
    public Libro() {
    }
    
    // Constructor completo
    public Libro(int idLibro, String titulo, String isbn, int anioPublicacion, 
                 int idAutor, boolean disponible, String genero, int paginas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.idAutor = idAutor;
        this.disponible = disponible;
        this.genero = genero;
        this.paginas = paginas;
    }
    
    // Getters y Setters
    public int getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public int getIdAutor() {
        return idAutor;
    }
    
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public int getPaginas() {
        return paginas;
    }
    
    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
    
    public String getNombreAutor() {
        return nombreAutor;
    }
    
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
}