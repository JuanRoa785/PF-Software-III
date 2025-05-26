package com.CRUD.Biblioteca.Repository;

import com.CRUD.Biblioteca.Model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    Optional<Carrito> findByUsuarioId(Integer idUsuario);

    @Query("SELECT c FROM Carrito c WHERE c.usuario.email = :correoUsuario")
    Optional<Carrito> findByCorreoUsuario(@Param("correoUsuario") String correoUsuario);

}
