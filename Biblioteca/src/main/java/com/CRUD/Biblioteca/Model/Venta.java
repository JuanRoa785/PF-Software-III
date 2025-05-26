package com.CRUD.Biblioteca.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false)
    private Date fecha;

    private String observaciones;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVenta;

    public Venta() {super();}

    public Venta(Integer id, Usuario usuario, Date fecha, String observaciones, Double total, String direccion, List<DetalleVenta> detalleVenta) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.total = total;
        this.direccion = direccion;
        this.detalleVenta = detalleVenta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<DetalleVenta> getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(List<DetalleVenta> detalleVenta) {
        this.detalleVenta = detalleVenta;
    }
}
