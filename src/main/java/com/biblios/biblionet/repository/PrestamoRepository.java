package com.biblios.biblionet.repository;

import com.biblios.biblionet.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    /**
     * Busca todos los préstamos de un usuario dado (por su ID).
     */
    List<Prestamo> findByUsuarioId(Long usuarioId);

    List<Prestamo> findByUsuarioNumeroCuenta(String numeroCuenta);

    /**
     * Busca todos los prestamos de un usuario que no ha devuelto
     * @param numeroCuenta
     * @return
     */
    List<Prestamo> findByUsuarioNumeroCuentaAndFechaDevolucionIsNull(String numeroCuenta);


    /**
     * Busca todos los préstamos de un libro dado (por su ID).
     */
    List<Prestamo> findByLibroId(Long libroId);

    /**
     * Busca todos los préstamos cuyo campo fechaDevolucion aún es null
     * (es decir, que no han sido devueltos).
     */
    List<Prestamo> findByFechaDevolucionIsNull();

    /**
     * Busca todos los préstamos cuya fecha de préstamo está entre dos fechas.
     */
    List<Prestamo> findByFechaPrestamoBetween(LocalDate desde, LocalDate hasta);

    /**
     * Devuelve true si existe al menos un préstamo para el libro dado (por su ID)
     * cuya fechaDevolucion sea NULL (préstamo activo).
     */
    boolean existsByLibroIdAndFechaDevolucionIsNull(Long id);
}