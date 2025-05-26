package com.CRUD.Biblioteca.DTO;

import java.util.List;

public class CarritoDTO {
    private Integer id;
    private Double total;
    private List<DetalleCarritoDTO> detalleCarrito;

    public CarritoDTO(Integer id, Double total, List<DetalleCarritoDTO> detalleCarrito) {
        this.id = id;
        this.total = total;
        this.detalleCarrito = detalleCarrito;
    }

    public CarritoDTO() {super();}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleCarritoDTO> getDetalleCarrito() {
        return detalleCarrito;
    }

    public void setDetalleCarrito(List<DetalleCarritoDTO> detalleCarrito) {
        this.detalleCarrito = detalleCarrito;
    }
}
