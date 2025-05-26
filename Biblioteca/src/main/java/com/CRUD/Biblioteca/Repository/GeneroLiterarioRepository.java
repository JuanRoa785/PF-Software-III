package com.CRUD.Biblioteca.Repository;

import com.CRUD.Biblioteca.Model.GeneroLiterario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GeneroLiterarioRepository extends JpaRepository<GeneroLiterario, Integer> {

    @Query(value = """
        SELECT COUNT(*) AS cantidad_libros,\s
               gl.id AS genero_id,\s
               gl.nombre AS genero_nombre,\s
               gl.portada AS genero_portada
        FROM libro l
        JOIN genero_literario gl ON l.id_genero_literario = gl.id
        GROUP BY gl.id, gl.nombre, gl.portada
        ORDER BY cantidad_libros DESC
       \s""", nativeQuery = true)
    List<Object[]> findGenerosConCantidadLibrosNative();

}
