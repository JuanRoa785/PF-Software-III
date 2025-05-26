package com.CRUD.Biblioteca.Repository;

import com.CRUD.Biblioteca.Model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findAllByUsuarioId(Integer id_usuario);

    Direccion findByUsuarioId(Integer id_usuario);

}
