package com.CRUD.Biblioteca.Controller;

import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.Libro;
import com.CRUD.Biblioteca.Service.GeneroLiterarioService;
import com.CRUD.Biblioteca.Service.LibroService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/libro")
public class LibroController {

    private final GeneroLiterarioService generoLiterarioService;
    private final LibroService libroService;
    
    @Autowired
    private Cloudinary cloudinary;

    public LibroController(GeneroLiterarioService generoLiterarioService, LibroService libroService) {
        this.generoLiterarioService = generoLiterarioService;
        this.libroService = libroService;
    }

    @GetMapping("/generos-ordenados")
    public ResponseEntity<List<Map<String, Object>>> getGenerosOrdenadosPorLibros() {
        return ResponseEntity.ok(generoLiterarioService.obtenerGenerosOrdenadosPorLibros());
    }

    @GetMapping("/listarEditoriales")
    public ResponseEntity<List<String>> obtenerEditoriales() {
        return ResponseEntity.ok(libroService.findDistinctEditoriales());
    }

    @GetMapping("/listarLibros")
    public ResponseEntity<List<Libro>> obtenerLibros() {
        return ResponseEntity.ok(libroService.findAll());
    }

    @PostMapping(value = "/crearLibro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearLibro(
            @RequestPart("libro") Libro libro,
            @RequestPart("imagen") MultipartFile imagen,
            @RequestHeader("Authorization") String token) {

        try {
            // Subir imagen a Cloudinary y establecer portada e id_portada
            
            Map<String, Object> options = new HashMap<>();
            options.put("folder", "Libros"); // la carpeta en Cloudinary

            Map uploadResult = cloudinary.uploader().upload(imagen.getBytes(), options);
            libro.setPortada(uploadResult.get("secure_url").toString());
            libro.setId_portada(uploadResult.get("public_id").toString());
            
            return ResponseEntity.ok(libroService.save(libro));
            
        } catch (IOException ex) {
            Logger.getLogger(LibroController.class.getName()).log(Level.SEVERE, "Error al subir la imagen a Cloudinary", ex);
        
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("No se pudo subir la portada del libro. Intenta de nuevo.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Integer id) {
        return libroService.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
    }

    @GetMapping("/filtrar_libros")
    public ResponseEntity<List<Libro>> filtrarLibros(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(defaultValue = "") String genero
    ) {
        List<Libro> libros = libroService.Filtro_libros(nombre, genero);
        return ResponseEntity.ok(libros);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> actualizarLibro(
            @PathVariable Integer id,
            @RequestPart("libro") Libro libroActualizado,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @RequestHeader("Authorization") String token) {

        try {
            // Buscar libro actual en BD
            Optional<Libro> libroOpt = libroService.findById(id);
            
            if (libroOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Libro libroExistente = libroOpt.get();
            String publicId = libroExistente.getId_portada();

            if (!publicId.startsWith("Libros/")) {
                publicId = "Libros/" + publicId;
            }

            // Si se envió imagen, subir a Cloudinary y actualizar datos de portada
            if (imagen != null && !imagen.isEmpty()) {
                // Opciones para subir a carpeta "Libros"
                Map<String, Object> options = new HashMap<>();
                options.put("folder", "Libros");

                // Eliminar la imagen anterior en Cloudinary si existe
                if (libroExistente.getId_portada() != null && !libroExistente.getId_portada().isEmpty()) {
                    cloudinary.uploader().destroy(libroExistente.getId_portada(), ObjectUtils.emptyMap());
                }

                // Subir nueva imagen
                Map uploadResult = cloudinary.uploader().upload(imagen.getBytes(), options);
                libroActualizado.setPortada(uploadResult.get("secure_url").toString());
                libroActualizado.setId_portada(uploadResult.get("public_id").toString());
            } else {
                // No se cambió la imagen, conservar la anterior
                libroActualizado.setPortada(libroExistente.getPortada());
                libroActualizado.setId_portada(libroExistente.getId_portada());
            }

            // Actualizar libro en BD, manteniendo id y relaciones necesarias
            libroActualizado.setId(id);
            Libro libroActualizadoGuardado = libroService.save(libroActualizado);

            return ResponseEntity.ok(libroActualizadoGuardado);

        } catch (IOException ex) {
            Logger.getLogger(LibroController.class.getName()).log(Level.SEVERE, "Error al subir la imagen a Cloudinary", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("No se pudo subir la imagen del libro. Intenta de nuevo.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarLibro(@PathVariable Integer id) {
        try {
            Optional<Libro> optionalLibro = libroService.findById(id);
            if (optionalLibro.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Libro libro = optionalLibro.get();
            String publicId = libro.getId_portada();

            if (!publicId.startsWith("Libros/")) {
                publicId = "Libros/" + publicId;
            }

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            libroService.deleteById(id);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Libro y portada eliminados correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el libro.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
