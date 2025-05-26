package com.CRUD.Biblioteca.Reponse;

public record AuthRequest(
        String email,
        String password
) {
}