package com.CRUD.Biblioteca.Repository;

import com.CRUD.Biblioteca.Model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    List<DetalleVenta> findByVentaId(Integer ventaId);

    @Query("SELECT d FROM DetalleVenta d WHERE d.libro.id = :idLibro")
    List<DetalleVenta> findByProductoId(@Param("idLibro") Integer idLibro);

    @Query("SELECT d FROM DetalleVenta d WHERE d.venta.usuario.id = :usuarioId")
    List<DetalleVenta> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT d FROM DetalleVenta d WHERE d.venta.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<DetalleVenta> findByFechaBetween(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
