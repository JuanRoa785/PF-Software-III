package com.CRUD.Biblioteca.Reponse;

public record RegisterRequest(
        String name,
        String apellidos,
        String correo,
        String contrasena,
        String telefono
) {
}
