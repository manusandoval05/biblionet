package com.biblios.biblionet.controller;

import com.biblios.biblionet.model.Libro;
import com.biblios.biblionet.model.Usuario;
import com.biblios.biblionet.repository.LibroRepository;
import com.biblios.biblionet.repository.PrestamoRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")

public class LibroController {
     private final LibroRepository libroRepo;
     private final PrestamoRepository prestamoRepo;

    // Inyección de dependencia vía constructor
    public LibroController(LibroRepository libroRepo, PrestamoRepository prestamoRepo) {
        this.libroRepo = libroRepo;
        this.prestamoRepo = prestamoRepo;
    }

    // GET /api/libros → devuelve todos los libros en un arreglo JSON
    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepo.findAll();
    }
    @PostMapping("/crear")
    public Libro crearLibro(@RequestBody Libro libro){
        return libroRepo.save(libro);
    }
    @GetMapping("/id/{id}")
    public Libro obtenerLibroPorId(@PathVariable Long id){
        return libroRepo.findById(id).orElse(null);
    }
    @GetMapping("/isbn/{isbn}")
    public Libro obtenerLibroPorIsbn(@PathVariable String isbn){
        return libroRepo.findByIsbn(isbn).orElse(null);
    }
    @GetMapping("/titulo/buscar")
    public List<Libro> buscarPorTitulo(@RequestParam("titulo") String titulo){
        return libroRepo.findByTituloContainingIgnoreCase(titulo);
    }
    /**
     * GET /api/libros/disponibles
     * Devuelve todos los libros que no estén en un préstamo activo (fechaDevolucion NULL).
     */
    @GetMapping("/disponibles")
    public List<Libro> listarLibrosDisponibles() {
        return libroRepo.findAllAvailable();
    }

    @GetMapping("/id/{id}/disponible")
    public boolean isDisponible(@PathVariable("id") Long id){
        boolean tienePrestamoActivo = prestamoRepo.existsByLibroIdAndFechaDevolucionIsNull(id);
        
        return !tienePrestamoActivo;
    }
}
