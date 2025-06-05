package com.biblios.biblionet.repository;

import com.biblios.biblionet.model.Libro;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findByTituloContainingIgnoreCase(String fragmento);

    /**
     * Devuelve todos los libros que NO están actualmente en préstamo activo.
     * (Es decir, aquellos cuyos IDs no aparecen en ningún Prestamo con fechaDevolucion IS NULL.)
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