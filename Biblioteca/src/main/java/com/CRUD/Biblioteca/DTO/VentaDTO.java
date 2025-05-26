package com.CRUD.Biblioteca.DTO;

import com.CRUD.Biblioteca.Model.Venta;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VentaDTO {
    private Integer id;
    private Date fecha;
    private String observaciones;
    private UsuarioDTO usuario;
    private Double total;
    private List<DetalleVentaDTO> detalleVenta;
    private String direccion;

    public VentaDTO(Integer id, Date fecha, String observaciones, UsuarioDTO usuario, Double total, List<DetalleVentaDTO> detalleVenta, String direccion) {
        this.id = id;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.usuario = usuario;
        this.total = total;
        this.detalleVenta = detalleVenta;
        this.direccion = direccion;
    }

    public VentaDTO() {super();}

    public VentaDTO(Venta venta) {
        if (venta == null) return;

        this.id = venta.getId();
        this.fecha = venta.getFecha();
        this.observaciones = venta.getObservaciones();
        this.total = venta.getTotal();
        this.direccion = venta.getDireccion();

        if (venta.getUsuario() != null) {
            this.usuario = new UsuarioDTO(venta.getUsuario());
        }

        if (venta.getDetalleVenta() != null) {
            this.detalleVenta = venta.getDetalleVenta().stream()
                    .map(DetalleVentaDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleVentaDTO> getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(List<DetalleVentaDTO> detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}