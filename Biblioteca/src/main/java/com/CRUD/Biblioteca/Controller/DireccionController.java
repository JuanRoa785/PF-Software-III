package com.CRUD.Biblioteca.Controller;

import com.CRUD.Biblioteca.DTO.DireccionDTO;
import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.Direccion;
import com.CRUD.Biblioteca.Model.Usuario;
import com.CRUD.Biblioteca.Repository.UsuarioRepository;
import com.CRUD.Biblioteca.Service.DireccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/direccion")
public class DireccionController {

    private final DireccionService direccionService;
    private final UsuarioRepository usuarioRepository;

    public DireccionController(DireccionService direccionService, UsuarioRepository usuarioRepository) {
        this.direccionService = direccionService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<DireccionDTO>> obtenerDireccionesPorUsuario(@PathVariable Integer idUsuario) {
        List<Direccion> direcciones = direccionService.findAllByUsuarioId(idUsuario);

        if (direcciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<DireccionDTO> direccionesDTO = direcciones.stream()
                .map(DireccionDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(direccionesDTO);
    }

    @GetMapping("/listarDirecciones")
    public ResponseEntity<?> obtenerDirecciones(@RequestHeader("Authorization") String token) {
        List<Direccion> direcciones = direccionService.findAll();
        List<DireccionDTO> direccionesDTO = direcciones.stream()
                .map(DireccionDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(direccionesDTO);
    }

    @PostMapping("/ingresarDireccion")
    public ResponseEntity<DireccionDTO> ingresarDireccion(@RequestBody Direccion direccion, @RequestHeader("Authorization") String token) {
        Integer idUsuario = direccion.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + idUsuario));

        direccion.setUsuario(usuario); // Asegura que esté bien persistido
        Direccion direccionGuardada = direccionService.save(direccion);

        return ResponseEntity.ok(new DireccionDTO(direccionGuardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> updateDireccion(
            @PathVariable Integer id,
            @RequestBody Direccion direccion,
            @RequestHeader("Authorization") String token) {

        return direccionService.findById(id)
                .map(existing -> {
                    direccion.setId(id);

                    // Aseguramos que el usuario esté bien persistido
                    Integer idUsuario = direccion.getUsuario().getId();
                    Usuario usuario = usuarioRepository.findById(idUsuario)
                            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + idUsuario));
                    direccion.setUsuario(usuario);

                    Direccion direccionActualizada = direccionService.save(direccion);
                    return ResponseEntity.ok(new DireccionDTO(direccionActualizada));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada con id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DireccionDTO> deleteDireccion(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        if (!direccionService.existsById(id)) {
            throw new ResourceNotFoundException("Dirección no encontrada con ID: " + id);
        }

        Direccion direccion = direccionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada con ID: " + id));

        direccionService.deleteById(id);

        return ResponseEntity.ok(new DireccionDTO(direccion));
    }

}
