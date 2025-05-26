package com.CRUD.Biblioteca.Service;

import com.CRUD.Biblioteca.Exception.ResourceNotFoundException;
import com.CRUD.Biblioteca.Model.Carrito;
import com.CRUD.Biblioteca.Model.TipoUsuario;
import com.CRUD.Biblioteca.Model.Token;
import com.CRUD.Biblioteca.Model.Usuario;
import com.CRUD.Biblioteca.Reponse.AuthRequest;
import com.CRUD.Biblioteca.Reponse.RegisterRequest;
import com.CRUD.Biblioteca.Reponse.TokenResponse;
import com.CRUD.Biblioteca.Reponse.UpdateUserRequest;
import com.CRUD.Biblioteca.Repository.TokenRepository;
import com.CRUD.Biblioteca.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private final UsuarioRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CarritoService carritoService;

    public AuthService(UsuarioRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, CarritoService carritoService) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.carritoService = carritoService;
    }

    @Transactional
    public TokenResponse register(final RegisterRequest request) {

        // Verificar si ya existe un usuario con ese correo
        if (repository.findByEmail(request.correo()).isPresent()) {
            throw new ResourceNotFoundException("El correo ya está registrado.");
        }

        final Usuario user = new Usuario();
        user.setNombres(request.name());
        user.setApellidos(request.apellidos());
        user.setTelefono(request.telefono());
        user.setEmail(request.correo());
        user.setTipoUsuario(new TipoUsuario(2));
        user.setContrasena(passwordEncoder.encode(request.contrasena()));
        user.setTokens(new ArrayList<>());

        final Usuario savedUser = repository.save(user);
        final String jwtToken = jwtService.generateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);

        Carrito carrito = new Carrito();
        carrito.setUsuario(savedUser);
        carrito.setTotal(0);
        carritoService.save(carrito);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }


    public TokenResponse update(
            final UpdateUserRequest request,
            final String authorizationHeader
    ) {
        // 1. Extraer el token actual del header (sin "Bearer ")
        final String currentAccessToken = authorizationHeader.substring(7);
        final String userEmail = jwtService.extractUsername(currentAccessToken);

        // 2. Buscar usuario por email
        final Usuario existingUser = repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Actualizar datos básicos
        existingUser.setNombres(request.name());
        existingUser.setApellidos(request.apellidos());
        existingUser.setTelefono(request.telefono());
        existingUser.setEmail(request.correo());

        boolean passwordChanged = false;

        // 4. Cambiar contraseña solo si se proporciona y es diferente
        if (request.contrasena() != null && !request.contrasena().isBlank()) {
            if (!passwordEncoder.matches(request.contrasena(), existingUser.getContrasena())) {
                existingUser.setContrasena(passwordEncoder.encode(request.contrasena()));
                passwordChanged = true;
            }
        }

        final Usuario updatedUser = repository.save(existingUser);

        // 5. Si la contraseña cambió, generar nuevos tokens
        if (passwordChanged) {
            revokeAllUserTokens(updatedUser);
            final String newAccessToken = jwtService.generateToken(updatedUser);
            final String newRefreshToken = jwtService.generateRefreshToken(updatedUser);
            saveUserToken(updatedUser, newAccessToken);
            return new TokenResponse(newAccessToken, newRefreshToken);
        }

        // 6. Si NO cambió la contraseña, devolver tokens actuales
        // Buscar el refresh_token actual usando el query
        Token currentRefreshTokenEntity = tokenRepository.findLatestValidTokenByUser(existingUser.getId());

        String currentRefreshToken = (currentRefreshTokenEntity != null)
                ? currentRefreshTokenEntity.getToken()
                : null;

        return new TokenResponse(currentAccessToken, currentRefreshToken);
    }

    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final Usuario user = repository.findByEmail(request.email())
                .orElseThrow();

        if(!user.isCuenta_activa()){
            throw new ResourceNotFoundException("El usuario no tiene cuenta activa");
        }
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(Usuario user, String jwtToken) {

        final Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(Token.TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final Usuario user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            return null;
        }

        final Usuario user = this.repository.findByEmail(userEmail).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
