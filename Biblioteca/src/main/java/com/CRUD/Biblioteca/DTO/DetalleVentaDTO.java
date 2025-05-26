package com.CRUD.Biblioteca.DTO;

import com.CRUD.Biblioteca.Model.DetalleVenta;
import com.CRUD.Biblioteca.Model.Libro;

public class DetalleVentaDTO {

    private Integer id;
    private Integer idVenta;
    private Libro libro;
    private Integer cantidad;

    public DetalleVentaDTO(Integer id, Integer idVenta, Libro libro, Integer cantidad) {
        this.id = id;
        this.idVenta = idVenta;
        this.libro = libro;
        this.cantidad = cantidad;
    }

    public DetalleVentaDTO() {super();}

    public DetalleVentaDTO(DetalleVenta detalleVenta) {
        if (detalleVenta != null) {
            this.id = detalleVenta.getId();
            this.idVenta = detalleVenta.getVenta().getId();
            this.libro = detalleVenta.getLibro();
            this.cantidad = detalleVenta.getCantidad();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
