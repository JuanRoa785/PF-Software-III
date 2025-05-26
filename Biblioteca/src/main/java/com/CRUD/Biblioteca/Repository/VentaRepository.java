package com.CRUD.Biblioteca.Repository;

import com.CRUD.Biblioteca.Model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query("""
           SELECT v
           FROM   Venta v
             JOIN v.detalleVenta d
           WHERE  d.libro.id = :idLibro
           ORDER  BY v.fecha DESC
           """)
    List<Venta> listarTodosFiltroDesc(@Param("idLibro") Integer idLibro);

    @Query("""
           SELECT v
           FROM   Venta v
             JOIN v.detalleVenta d
           WHERE  d.libro.id = :idLibro
           ORDER  BY v.fecha ASC
           """)
    List<Venta> listarTodosFiltroAsc(@Param("idLibro") Integer idLibro);

    List<Venta> findByUsuarioId(Integer idUsuario);

    @Query("""
           SELECT v
           FROM   Venta v
             JOIN v.detalleVenta d
           WHERE  v.usuario.id = :idUsuario
             AND  UPPER(d.libro.nombre) LIKE %:nombreLibro%
           ORDER  BY v.fecha DESC
           """)
    List<Venta> findUsuarioAndNombreLibroDesc(@Param("idUsuario") Integer idUsuario,
                                              @Param("nombreLibro") String nombreLibro);

    @Query("""
           SELECT v
           FROM   Venta v
             JOIN v.detalleVenta d
           WHERE  v.usuario.id = :idUsuario
             AND  UPPER(d.libro.nombre) LIKE %:nombreLibro%
           ORDER  BY v.fecha ASC
           """)
    List<Venta> findUsuarioAndNombreLibroAsc(@Param("idUsuario") Integer idUsuario,
                                             @Param("nombreLibro") String nombreLibro);

    @Query("""
           SELECT v
           FROM   Venta v
           WHERE  (
             UPPER(v.usuario.nombres) LIKE %:cliente%
             OR UPPER(v.usuario.apellidos) LIKE %:cliente%
           )
             AND  v.fecha BETWEEN :fechaInferior AND :fechaSuperior
           ORDER  BY v.total DESC
           """)
    List<Venta> reporteIndividualDesc(@Param("cliente") String cliente,
                                      @Param("fechaInferior") Date fechaInferior,
                                      @Param("fechaSuperior") Date fechaSuperior);

    @Query("""
           SELECT v
           FROM   Venta v
           WHERE  (
             UPPER(v.usuario.nombres) LIKE %:cliente%
             OR UPPER(v.usuario.apellidos) LIKE %:cliente%
           )
             AND  v.fecha BETWEEN :fechaInferior AND :fechaSuperior
           ORDER  BY v.total ASC
           """)
    List<Venta> reporteIndividualAsc(@Param("cliente") String cliente,
                                     @Param("fechaInferior") Date fechaInferior,
                                     @Param("fechaSuperior") Date fechaSuperior);
}
