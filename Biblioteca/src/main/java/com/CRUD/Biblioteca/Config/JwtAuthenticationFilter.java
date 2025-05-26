package com.CRUD.Biblioteca.Config;

import com.CRUD.Biblioteca.Repository.TokenRepository;
import com.CRUD.Biblioteca.Service.JwtService;
import com.CRUD.Biblioteca.Service.UserDetailsServiceImpl;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenRepository tokenRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Permitir libremente las rutas de autenticación
            if (request.getServletPath().contains("/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // Si no hay token, dejar pasar la request como anónima
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwt = authHeader.substring(7);

            // Validar existencia y vigencia del token en la base de datos
            boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(token -> !token.getExpired() && !token.getRevoked())
                    .orElse(false);

            if (!isTokenValid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"mensaje\": \"Token inválido o expirado\"}");
                return;
            }

            final String userEmail = jwtService.extractUsername(jwt);

            // Si no se puede extraer el usuario o ya hay autenticación en contexto, continuar
            if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValidation(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"Token JWT inválido\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

}