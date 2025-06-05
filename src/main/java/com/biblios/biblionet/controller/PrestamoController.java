package com.biblios.biblionet.controller;

import com.biblios.biblionet.model.Libro;
import com.biblios.biblionet.model.Prestamo;
import com.biblios.biblionet.model.Usuario;
import com.biblios.biblionet.repository.LibroRepository;
import com.biblios.biblionet.repository.PrestamoRepository;
import com.biblios.biblionet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestionar préstamos de libros a usuarios.
 * Expone endpoints bajo la ruta /api/prestamos.
 */
@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoRepository prestamoRepo;
    private final LibroRepository libroRepo;
    private final UsuarioRepository usuarioRepo;

    /**
     * Constructor que inyecta los repositorios necesarios para gestionar préstamos.
     *
     * @param prestamoRepo Repositorio de préstamos
     * @param libroRepo Repositorio de libros
     * @param usuarioRepo Repositorio de usuarios
     */
    public PrestamoController(PrestamoRepository prestamoRepo,
                              LibroRepository libroRepo,
                              UsuarioRepository usuarioRepo) {
        this.prestamoRepo = prestamoRepo;
        this.libroRepo = libroRepo;
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Devuelve todos los préstamos registrados en el sistema.
     *
     * @return Lista de préstamos
     */
    @GetMapping
    public List<Prestamo> listarTodos() {
        return prestamoRepo.findAll();
    }

    /**
     * Devuelve los préstamos pendientes (es decir, aquellos cuya fecha de devolución es nula).
     *
     * @return Lista de préstamos no devueltos
     */
    @GetMapping("/pendientes")
    public List<Prestamo> listarPendientes(){
        return prestamoRepo.findByFechaDevolucionIsNull();
    }

    /**
     * Devuelve un préstamo específico por su ID.
     *
     * @param id Identificador del préstamo
     * @return Objeto {@link Prestamo} si se encuentra, o 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        return prestamoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo préstamo a partir de los datos proporcionados (ISBN, número de cuenta, fecha de préstamo).
     *
     * @param dto DTO con los datos del préstamo
     * @return Préstamo creado
     */
    @PostMapping("/crear")
    public ResponseEntity<Prestamo> crearPrestamo(@RequestBody PrestamoDto dto) {
        Libro libro = libroRepo.findByIsbn(dto.getIsbn())
                .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con ISBN " + dto.getIsbn()));

        Usuario usuario = usuarioRepo.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con número de cuenta " + dto.getNumeroCuenta()));

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucion(null); // aún no devuelto

        Prestamo creado = prestamoRepo.save(prestamo);
        return ResponseEntity.ok(creado);
    }

    /**
     * Marca un préstamo como devuelto, estableciendo la fecha de devolución como la fecha actual.
     *
     * @param id ID del préstamo a actualizar
     * @return Préstamo actualizado con fecha de devolución
     */
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverPrestamo(@PathVariable Long id) {
        return prestamoRepo.findById(id)
                .map(p -> {
                    p.setFechaDevolucion(LocalDate.now());
                    Prestamo actualizado = prestamoRepo.save(p);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Devuelve todos los préstamos activos (sin devolver) de un usuario, usando su número de cuenta.
     *
     * @param numeroCuenta Número de cuenta del usuario
     * @return Lista de préstamos activos del usuario
     */
    @GetMapping("/por-cuenta")
    public List<Prestamo> buscarActivosPorCuenta(@RequestParam("numeroCuenta") String numeroCuenta) {
        return prestamoRepo.findByUsuarioNumeroCuentaAndFechaDevolucionIsNull(numeroCuenta);
    }

    /**
     * Devuelve todos los préstamos (activos o cerrados) de un libro por su ID.
     *
     * @param libroId ID del libro
     * @return Lista de préstamos relacionados con el libro
     */
    @GetMapping("/por-libro/{libroId}")
    public List<Prestamo> buscarPorLibro(@PathVariable Long libroId) {
        return prestamoRepo.findByLibroId(libroId);
    }

    /**
     * DTO utilizado para recibir datos al crear un préstamo.
     */
    public static class PrestamoDto {
        private String isbn;
        private String numeroCuenta;
        private LocalDate fechaPrestamo;

        /**
         * Constructor vacío necesario para deserialización.
         */
        public PrestamoDto() { }

        /**
         * Obtiene el ISBN del libro a prestar.
         * @return ISBN del libro
         */
        public String getIsbn() {
            return isbn;
        }

        /**
         * Establece el ISBN del libro a prestar.
         * @param isbn ISBN del libro
         */
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        /**
         * Obtiene el número de cuenta del usuario que toma el préstamo.
         * @return Número de cuenta del usuario
         */
        public String getNumeroCuenta() {
            return numeroCuenta;
        }

        /**
         * Establece el número de cuenta del usuario que toma el préstamo.
         * @param numeroCuenta Número de cuenta
         */
        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        /**
         * Obtiene la fecha del préstamo.
         * @return Fecha del préstamo
         */
        public LocalDate getFechaPrestamo() {
            return fechaPrestamo;
        }

        /**
         * Establece la fecha del préstamo.
         * @param fechaPrestamo Fecha del préstamo
         */
        public void setFechaPrestamo(LocalDate fechaPrestamo) {
            this.fechaPrestamo = fechaPrestamo;
        }
    }
}
