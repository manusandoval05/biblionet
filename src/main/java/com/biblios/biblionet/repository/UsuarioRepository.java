package com.biblios.biblionet.repository;

import com.biblios.biblionet.model.Usuario;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Usuario}.
 * Extiende {@link JpaRepository} para proporcionar operaciones CRUD
 * y consultas personalizadas sobre los usuarios registrados.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su número de cuenta.
     *
     * @param numeroCuenta Número de cuenta del usuario
     * @return Un {@link Optional} con el usuario si se encuentra, o vacío si no existe
     */
    Optional<Usuario> findByNumeroCuenta(String numeroCuenta);

    /**
     * Busca usuarios cuyo nombre contenga el texto dado (ignorando mayúsculas/minúsculas).
     *
     * @param fragmento Parte del nombre a buscar
     * @return Lista de usuarios que coincidan con el nombre parcial
     */
    List<Usuario> findByNombreContainingIgnoreCase(String fragmento);
}
