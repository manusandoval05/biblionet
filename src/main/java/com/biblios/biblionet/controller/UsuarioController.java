package com.biblios.biblionet.controller;

import com.biblios.biblionet.model.Usuario;
import com.biblios.biblionet.repository.UsuarioRepository;

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
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")

public class UsuarioController {
    private final UsuarioRepository usuarioRepo;

    // Inyección de dependencia vía constructor
    public UsuarioController(UsuarioRepository libroRepo) {
        this.usuarioRepo = libroRepo;
    }

    // GET /api/libros → devuelve todos los libros en un arreglo JSON
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepo.findAll();
    }
    @PostMapping("/crear")
    public Usuario crearUsuario(@RequestBody Usuario usuario){
        return usuarioRepo.save(usuario);
    }
    @GetMapping("/id/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id){
        return usuarioRepo.findById(id).orElse(null);
    }
    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public Usuario obtenerUsuarioPorCuenta(@PathVariable String numeroCuenta){
        return usuarioRepo.findByNumeroCuenta(numeroCuenta).orElse(null);
    }
    @GetMapping("/nombre/buscar")
    public List<Usuario> buscarPorNombre(@RequestParam("nombre") String nombre){
        return usuarioRepo.findByNombreContainingIgnoreCase(nombre);
    }
}
