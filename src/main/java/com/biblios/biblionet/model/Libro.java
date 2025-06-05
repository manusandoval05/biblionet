package com.biblios.biblionet.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa un libro dentro del sistema de gestión de biblioteca.
 * Cada instancia de esta clase corresponde a un registro en la tabla "libros" de la base de datos.
 */
@Entity
@Table(name = "libros")
public class Libro {

    /**
     * Identificador único del libro. Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Título del libro. No puede ser nulo y tiene un máximo de 200 caracteres.
     */
    @Column(nullable = false, length = 200)
    private String titulo;

    /**
     * Autor del libro. No puede ser nulo y tiene un máximo de 150 caracteres.
     */
    @Column(nullable = false, length = 150)
    private String autor;

    /**
     * ISBN del libro. Debe ser único y tiene un máximo de 20 caracteres.
     */
    @Column(unique = true, length = 20)
    private String isbn;

    /**
     * Género literario del libro. No puede ser nulo y tiene un máximo de 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    private String genero;

    /**
     * Fecha de publicación del libro. Puede ser nula.
     */
    private LocalDate fechaPublicacion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Libro() {
        // Constructor vacío (requerido por JPA)
    }

    /**
     * Constructor que permite inicializar un libro con todos sus atributos.
     *
     * @param titulo            Título del libro
     * @param autor             Autor del libro
     * @param isbn              Código ISBN del libro
     * @param fechaPublicacion  Fecha de publicación del libro
     * @param genero            Género literario del libro
     */
    public Libro(String titulo, String autor, String isbn, LocalDate fechaPublicacion, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
        this.fechaPublicacion = fechaPublicacion; 
    }

    // ——— Getters y Setters ———

    /**
     * Obtiene el identificador del libro.
     *
     * @return ID del libro
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del libro.
     *
     * @param id ID a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return Título del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo Título a establecer
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del libro.
     *
     * @return Autor del libro
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     *
     * @param autor Autor a establecer
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene el ISBN del libro.
     *
     * @return ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro.
     *
     * @param isbn ISBN a establecer
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el género del libro.
     *
     * @return Género del libro
     */
    public String getGenero(){
        return genero;
    }

    /**
     * Establece el género del libro.
     *
     * @param genero Género a establecer
     */
    public void setGenero(String genero){
        this.genero = genero;
    }

    /**
     * Obtiene la fecha de publicación del libro.
     *
     * @return Fecha de publicación
     */
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece la fecha de publicación del libro.
     *
     * @param fechaPublicacion Fecha a establecer
     */
    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
