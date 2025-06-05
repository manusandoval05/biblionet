package com.biblios.biblionet.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa un préstamo de un libro a un usuario dentro del sistema de biblioteca.
 * Cada instancia de esta clase corresponde a un registro en la tabla "prestamos" de la base de datos.
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {

    /**
     * Identificador único del préstamo. Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Libro prestado. Relación muchos a uno con la entidad Libro.
     * No puede ser nulo.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    /**
     * Usuario que realiza el préstamo. Relación muchos a uno con la entidad Usuario.
     * No puede ser nulo.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Fecha en que se realizó el préstamo. No puede ser nula.
     */
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    /**
     * Fecha en que se devolvió el libro. Puede ser nula si el libro aún no ha sido devuelto.
     */
    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Prestamo() {
        // Constructor vacío requerido por JPA
    }

    /**
     * Constructor con todos los atributos del préstamo.
     *
     * @param libro           Libro prestado
     * @param usuario         Usuario que toma el préstamo
     * @param fechaPrestamo   Fecha en que se realiza el préstamo
     * @param fechaDevolucion Fecha de devolución del libro (puede ser nula)
     */
    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // ——— Getters y Setters ———

    /**
     * Obtiene el ID del préstamo.
     *
     * @return ID del préstamo
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del préstamo.
     *
     * @param id ID a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el libro prestado.
     *
     * @return Libro prestado
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Establece el libro prestado.
     *
     * @param libro Libro a establecer
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene el usuario que realizó el préstamo.
     *
     * @return Usuario que tomó el libro prestado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realiza el préstamo.
     *
     * @param usuario Usuario a establecer
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la fecha del préstamo.
     *
     * @return Fecha del préstamo
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Establece la fecha del préstamo.
     *
     * @param fechaPrestamo Fecha a establecer
     */
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Obtiene la fecha de devolución del libro.
     *
     * @return Fecha de devolución, o null si aún no se ha devuelto
     */
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Establece la fecha de devolución del libro.
     *
     * @param fechaDevolucion Fecha a establecer (puede ser null)
     */
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}