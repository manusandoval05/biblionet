package com.biblios.biblionet.repository;

import com.biblios.biblionet.model.Usuario;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // JpaRepository ya proporciona findAll(), findById(), save(), delete(), etc.
    Optional<Usuario> findByNumeroCuenta(String numeroCuenta);

    List<Usuario> findByNombreContainingIgnoreCase(String fragmento);
}