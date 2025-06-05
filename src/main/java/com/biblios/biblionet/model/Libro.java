package com.biblios.biblionet.model;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String genero;

    private LocalDate fechaPublicacion;

    public Libro() {
        // Constructor vacío (requerido por JPA)
    }

    public Libro(String titulo, String autor, String isbn, LocalDate fechaPublicacion, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
        this.fechaPublicacion = fechaPublicacion; 
    }

    // ——— Getters y Setters ———

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getGenero(){
        return genero;
    }
    public void setGenero(String genero){
        this.genero = genero;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}