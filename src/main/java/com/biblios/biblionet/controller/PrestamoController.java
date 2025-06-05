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

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoRepository prestamoRepo;
    private final LibroRepository libroRepo;
    private final UsuarioRepository usuarioRepo;

    public PrestamoController(PrestamoRepository prestamoRepo,
                              LibroRepository libroRepo,
                              UsuarioRepository usuarioRepo) {
        this.prestamoRepo = prestamoRepo;
        this.libroRepo = libroRepo;
        this.usuarioRepo = usuarioRepo;
    }

    /** GET /api/prestamos → devuelve todos los préstamos **/
    @GetMapping
    public List<Prestamo> listarTodos() {
        return prestamoRepo.findAll();
    }

    @GetMapping("/pendientes")
    public List<Prestamo> listarPendientes(){
        return prestamoRepo.findByFechaDevolucionIsNull();
    }

    /** GET /api/prestamos/{id} → devuelve un préstamo por su ID **/
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        return prestamoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/prestamos/crear
     * Crea un nuevo préstamo buscando Libro por ISBN y Usuario por numeroCuenta.
     * JSON de entrada debe incluir:
     *   - "isbn": ISBN del libro existente
     *   - "numeroCuenta": número de cuenta del usuario existente
     *   - "fechaPrestamo": en formato "yyyy-MM-dd"
     */
    @PostMapping("/crear")
    public ResponseEntity<Prestamo> crearPrestamo(@RequestBody PrestamoDto dto) {
        // 1) Buscar libro por ISBN
        Libro libro = libroRepo.findByIsbn(dto.getIsbn())
                .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado con ISBN " + dto.getIsbn()));
        // 2) Buscar usuario por número de cuenta
        Usuario usuario = usuarioRepo.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con número de cuenta " + dto.getNumeroCuenta()));

        // 3) Crear y guardar préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucion(null); // aún no devuelto

        Prestamo creado = prestamoRepo.save(prestamo);
        return ResponseEntity.ok(creado);
    }

    /**
     * PUT /api/prestamos/{id}/devolver
     * Marca la fechaDevolucion del préstamo como la fecha actual.
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
     * GET /api/prestamos/por-cuenta
     * ?numeroCuenta=XYZ
     * Devuelve todos los préstamos activos (fechaDevolucion IS NULL) de un usuario por número de cuenta.
     */
    @GetMapping("/por-cuenta")
    public List<Prestamo> buscarActivosPorCuenta(@RequestParam("numeroCuenta") String numeroCuenta) {
        return prestamoRepo.findByUsuarioNumeroCuentaAndFechaDevolucionIsNull(numeroCuenta);
    }

    /**
     * GET /api/prestamos/por-libro/{libroId}
     * Devuelve todos los préstamos (activos o cerrados) de un libro por su ID.
     */
    @GetMapping("/por-libro/{libroId}")
    public List<Prestamo> buscarPorLibro(@PathVariable Long libroId) {
        return prestamoRepo.findByLibroId(libroId);
    }

    // DTO para recibir datos de creación de préstamo
    public static class PrestamoDto {
        private String isbn;
        private String numeroCuenta;
        private LocalDate fechaPrestamo;

        public PrestamoDto() { }

        public String getIsbn() {
            return isbn;
        }
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getNumeroCuenta() {
            return numeroCuenta;
        }
        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public LocalDate getFechaPrestamo() {
            return fechaPrestamo;
        }
        public void setFechaPrestamo(LocalDate fechaPrestamo) {
            this.fechaPrestamo = fechaPrestamo;
        }
    }
}
