package com.CRUD.Biblioteca.Reponse;

public record UpdateUserRequest(
        String name,
        String apellidos,
        String correo,
        String contrasena,
        String telefono
) {
}
