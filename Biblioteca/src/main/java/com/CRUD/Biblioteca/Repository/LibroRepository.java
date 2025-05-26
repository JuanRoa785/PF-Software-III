package com.CRUD.Biblioteca.Repository;

import com.CRUD.Biblioteca.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    @Query(value = """
    SELECT l.*
    FROM libro l
    JOIN genero_literario g ON l.id_genero_literario = g.id
    WHERE (:nombre = '' OR LOWER(l.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
      AND (:genero = '' OR LOWER(g.nombre) LIKE LOWER(CONCAT('%', :genero, '%')))
    ORDER BY l.fecha_publicacion DESC
    """, nativeQuery = true)
    List<Libro> Filtro_libros(@Param("nombre") String nombre, @Param("genero") String genero);

    @Query(value = "SELECT editorial FROM libro GROUP BY editorial ORDER BY COUNT(*) DESC", nativeQuery = true)
    List<String> findDistinctEditoriales();

}
