package com.biblios.biblionet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, nullable = false, length = 15)
    private String numeroCuenta;


    public Usuario() {
        // Constructor vacío requerido por JPA
    }

    public Usuario(String nombre, String numeroCuenta) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
    }

    // ——— Getters y Setters ———

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNumeroCuenta(){
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta){
        this.numeroCuenta = numeroCuenta;
    }
}
