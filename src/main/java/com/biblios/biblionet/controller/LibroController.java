package com.biblios.biblionet.controller;

import com.biblios.biblionet.model.Libro;
import com.biblios.biblionet.repository.LibroRepository;
import com.biblios.biblionet.repository.PrestamoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los libros.
 * Expone endpoints bajo la ruta /api/libros para consultar, crear y buscar libros.
 */
@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroRepository libroRepo;
    private final PrestamoRepository prestamoRepo;

    /**
     * Constructor con inyección de dependencias para los repositorios de libro y préstamo.
     *
     * @param libroRepo      Repositorio de libros
     * @param prestamoRepo   Repositorio de préstamos
     */
    public LibroController(LibroRepository libroRepo, PrestamoRepository prestamoRepo) {
        this.libroRepo = libroRepo;
        this.prestamoRepo = prestamoRepo;
    }

    /**
     * Obtiene la lista de todos los libros registrados en el sistema.
     *
     * @return Lista de objetos {@link Libro}
     */
    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepo.findAll();
    }

    /**
     * Crea un nuevo libro a partir del objeto recibido en el cuerpo de la petición.
     *
     * @param libro Objeto {@link Libro} a crear
     * @return Libro creado y guardado en la base de datos
     */
    @PostMapping("/crear")
    public Libro crearLibro(@RequestBody Libro libro){
        return libroRepo.save(libro);
    }

    /**
     * Obtiene un libro a partir de su ID.
     *
     * @param id Identificador del libro
     * @return Objeto {@link Libro} si existe, de lo contrario null
     */
    @GetMapping("/id/{id}")
    public Libro obtenerLibroPorId(@PathVariable Long id){
        return libroRepo.findById(id).orElse(null);
    }

    /**
     * Obtiene un libro a partir de su código ISBN.
     *
     * @param isbn Código ISBN del libro
     * @return Objeto {@link Libro} si se encuentra, de lo contrario null
     */
    @GetMapping("/isbn/{isbn}")
    public Libro obtenerLibroPorIsbn(@PathVariable String isbn){
        return libroRepo.findByIsbn(isbn).orElse(null);
    }

    /**
     * Busca libros cuyo título contenga el texto proporcionado (ignorando mayúsculas/minúsculas).
     *
     * @param titulo Parte del título a buscar
     * @return Lista de libros coincidentes
     */
    @GetMapping("/titulo/buscar")
    public List<Libro> buscarPorTitulo(@RequestParam("titulo") String titulo){
        return libroRepo.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * Devuelve una lista de todos los libros disponibles (no prestados actualmente).
     *
     * @return Lista de libros disponibles
     */
    @GetMapping("/disponibles")
    public List<Libro> listarLibrosDisponibles() {
        return libroRepo.findAllAvailable();
    }

    /**
     * Verifica si un libro está disponible (es decir, no tiene préstamos activos).
     *
     * @param id ID del libro
     * @return true si el libro está disponible, false si está prestado
     */
    @GetMapping("/id/{id}/disponible")
    public boolean isDisponible(@PathVariable("id") Long id){
        boolean tienePrestamoActivo = prestamoRepo.existsByLibroIdAndFechaDevolucionIsNull(id);
        return !tienePrestamoActivo;
    }
}