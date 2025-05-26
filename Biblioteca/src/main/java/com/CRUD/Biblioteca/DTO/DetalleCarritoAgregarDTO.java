package com.CRUD.Biblioteca.DTO;

import org.antlr.v4.runtime.misc.NotNull;

public class DetalleCarritoAgregarDTO {

    @NotNull
    private Integer idCarrito;

    @NotNull
    private Integer idLibro;

    @NotNull
    private Integer cantidad;

    public DetalleCarritoAgregarDTO(Integer idCarrito, Integer idLibro, Integer cantidad) {
        this.idCarrito = idCarrito;
        this.idLibro = idLibro;
        this.cantidad = cantidad;
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
