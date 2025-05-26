package com.CRUD.Biblioteca.DTO;

import com.CRUD.Biblioteca.Model.Libro;

public class DetalleCarritoDTO {
    private Integer id;
    private Integer idCarrito;
    private Libro libro;
    private Integer cantidad;

    public DetalleCarritoDTO() {super();}

    public DetalleCarritoDTO(Integer id, Integer idCarrito, Libro libro, Integer cantidad) {
        this.id = id;
        this.idCarrito = idCarrito;
        this.libro = libro;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
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
