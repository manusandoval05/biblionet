package com.biblios.biblionet.controller;

import com.biblios.biblionet.model.Usuario;
import com.biblios.biblionet.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar usuarios del sistema.
 * Expone endpoints bajo la ruta /api/usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param libroRepo Repositorio de usuarios
     */
    public UsuarioController(UsuarioRepository libroRepo) {
        this.usuarioRepo = libroRepo;
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return Lista de objetos {@link Usuario}
     */
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepo.findAll();
    }

    /**
     * Crea un nuevo usuario a partir del objeto recibido.
     *
     * @param usuario Objeto {@link Usuario} a crear
     * @return Usuario creado
     */
    @PostMapping("/crear")
    public Usuario crearUsuario(@RequestBody Usuario usuario){
        return usuarioRepo.save(usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Usuario correspondiente si existe, o null si no se encuentra
     */
    @GetMapping("/id/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id){
        return usuarioRepo.findById(id).orElse(null);
    }

    /**
     * Obtiene un usuario por su número de cuenta.
     *
     * @param numeroCuenta Número de cuenta del usuario
     * @return Usuario correspondiente si existe, o null si no se encuentra
     */
    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public Usuario obtenerUsuarioPorCuenta(@PathVariable String numeroCuenta){
        return usuarioRepo.findByNumeroCuenta(numeroCuenta).orElse(null);
    }

    /**
     * Busca usuarios cuyo nombre contenga el texto dado (ignorando mayúsculas/minúsculas).
     *
     * @param nombre Parte del nombre del usuario a buscar
     * @return Lista de usuarios que coinciden con el criterio de búsqueda
     */
    @GetMapping("/nombre/buscar")
    public List<Usuario> buscarPorNombre(@RequestParam("nombre") String nombre){
        return usuarioRepo.findByNombreContainingIgnoreCase(nombre);
    }
}
