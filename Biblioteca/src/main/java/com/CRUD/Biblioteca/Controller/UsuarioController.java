package com.CRUD.Biblioteca.Controller;

import com.CRUD.Biblioteca.DTO.UsuarioDTO;
import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.Usuario;
import com.CRUD.Biblioteca.Reponse.AuthRequest;
import com.CRUD.Biblioteca.Reponse.RegisterRequest;
import com.CRUD.Biblioteca.Reponse.TokenResponse;
import com.CRUD.Biblioteca.Reponse.UpdateUserRequest;
import com.CRUD.Biblioteca.Repository.UsuarioRepository;
import com.CRUD.Biblioteca.Service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    private final AuthService service;
    private final UsuarioRepository repository;

    public UsuarioController(AuthService service, UsuarioRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/listUserById")
    public ResponseEntity<UsuarioDTO> listarUsuariosById(@RequestParam Integer id,
                                                         @RequestHeader("Authorization") String token) {
        UsuarioDTO usuario = repository.findById(id)
                .map(UsuarioDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el usuario con id: " + id));
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/listUser")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(@RequestHeader("Authorization") String token) {
        List<Usuario> usuarios = repository.findAll();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/listByEmail")
    public ResponseEntity<UsuarioDTO> listarUsuariosByEmail(@RequestParam("email") String email,
                                                            @RequestHeader("Authorization") String token) {
        UsuarioDTO usuario = repository.findByEmail(email)
                .map(UsuarioDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el usuario con correo: " + email));
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        final TokenResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request) {
        final TokenResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    ) {
        return service.refreshToken(authentication);
    }

    @PutMapping("/update")
    public ResponseEntity<TokenResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            @RequestHeader("Authorization") String token
    ) {
        TokenResponse response = service.update(request, token);
        return ResponseEntity.ok(response);
    }

}
