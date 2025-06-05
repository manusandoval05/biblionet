package com.biblios.biblionet.model;

import jakarta.persistence.*;

/**
 * Representa un usuario registrado en el sistema de biblioteca.
 * Cada instancia corresponde a un registro en la tabla "usuarios" de la base de datos.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario. Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nombre completo del usuario. No puede ser nulo y tiene un máximo de 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Número de cuenta del usuario. Es único, no nulo, y tiene un máximo de 15 caracteres.
     */
    @Column(unique = true, nullable = false, length = 15)
    private String numeroCuenta;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Usuario() {
        // Constructor vacío requerido por JPA
    }

    /**
     * Constructor para inicializar un usuario con nombre y número de cuenta.
     *
     * @param nombre        Nombre completo del usuario
     * @param numeroCuenta  Número de cuenta único del usuario
     */
    public Usuario(String nombre, String numeroCuenta) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
    }

    // ——— Getters y Setters ———

    /**
     * Obtiene el ID del usuario.
     *
     * @return ID del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param id ID a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre Nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de cuenta del usuario.
     *
     * @return Número de cuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Establece el número de cuenta del usuario.
     *
     * @param numeroCuenta Número de cuenta a establecer
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
