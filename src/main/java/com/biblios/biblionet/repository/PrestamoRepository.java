package com.biblios.biblionet.repository;

import com.biblios.biblionet.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad {@link Prestamo}.
 * Proporciona operaciones CRUD y consultas personalizadas sobre préstamos de libros.
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    /**
     * Busca todos los préstamos asociados a un usuario específico por su ID.
     *
     * @param usuarioId ID del usuario
     * @return Lista de préstamos realizados por el usuario
     */
    List<Prestamo> findByUsuarioId(Long usuarioId);

    /**
     * Busca todos los préstamos asociados a un usuario por su número de cuenta.
     *
     * @param numeroCuenta Número de cuenta del usuario
     * @return Lista de préstamos del usuario
     */
    List<Prestamo> findByUsuarioNumeroCuenta(String numeroCuenta);

    /**
     * Busca todos los préstamos activos (no devueltos) de un usuario por número de cuenta.
     *
     * @param numeroCuenta Número de cuenta del usuario
     * @return Lista de préstamos activos del usuario
     */
    List<Prestamo> findByUsuarioNumeroCuentaAndFechaDevolucionIsNull(String numeroCuenta);

    /**
     * Busca todos los préstamos asociados a un libro por su ID.
     *
     * @param libroId ID del libro
     * @return Lista de préstamos realizados para el libro
     */
    List<Prestamo> findByLibroId(Long libroId);

    /**
     * Busca todos los préstamos pendientes, es decir, aquellos que no han sido devueltos.
     *
     * @return Lista de préstamos con fechaDevolucion = NULL
     */
    List<Prestamo> findByFechaDevolucionIsNull();

    /**
     * Busca todos los préstamos realizados entre dos fechas específicas.
     *
     * @param desde Fecha de inicio (inclusive)
     * @param hasta Fecha de fin (inclusive)
     * @return Lista de préstamos realizados en el rango de fechas
     */
    List<Prestamo> findByFechaPrestamoBetween(LocalDate desde, LocalDate hasta);

    /**
     * Verifica si existe al menos un préstamo activo (sin devolver) para un libro dado.
     *
     * @param id ID del libro
     * @return true si hay préstamos activos para el libro, false en caso contrario
     */
    boolean existsByLibroIdAndFechaDevolucionIsNull(Long id);
}
