package com.biblios.biblionet.repository;

import com.biblios.biblionet.model.Libro;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Libro}.
 * Proporciona métodos CRUD y consultas personalizadas para interactuar con la base de datos.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    /**
     * Busca un libro por su ISBN.
     *
     * @param isbn Código ISBN del libro
     * @return Un {@link Optional} con el libro si se encuentra, o vacío si no existe
     */
    Optional<Libro> findByIsbn(String isbn);

    /**
     * Busca libros cuyo título contenga el texto dado, sin importar mayúsculas/minúsculas.
     *
     * @param fragmento Fragmento del título a buscar
     * @return Lista de libros que coinciden con el título
     */
    List<Libro> findByTituloContainingIgnoreCase(String fragmento);

    /**
     * Devuelve todos los libros que **no están en préstamo activo**.
     * Es decir, aquellos cuyos IDs no aparecen en préstamos con `fechaDevolucion IS NULL`.
     *
     * @return Lista de libros actualmente disponibles para préstamo
     */
    @Query("""
      SELECT l 
        FROM Libro l 
       WHERE l.id NOT IN (
         SELECT p.libro.id 
           FROM Prestamo p 
          WHERE p.fechaDevolucion IS NULL
       )
    """)
    List<Libro> findAllAvailable();
}
