package com.CRUD.Biblioteca.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false)
    private String numero_tarjeta;

    @Column(nullable = false)
    private Date fecha_expiracion;

    @Column(nullable = false)
    private String nombre_titular;

    @Column(nullable = false)
    private String tipo_tarjeta;


    public TarjetaCredito() {super();}

    public TarjetaCredito(Integer id, String nombre_titular, String numero_tarjeta, Date fecha_expiracion, String tipo_tarjeta, Usuario usuario) {
        this.id = id;
        this.nombre_titular = nombre_titular;
        this.numero_tarjeta = numero_tarjeta;
        this.fecha_expiracion = fecha_expiracion;
        this.tipo_tarjeta = tipo_tarjeta;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre_titular() {
        return nombre_titular;
    }

    public void setNombre_titular(String nombre_titular) {
        this.nombre_titular = nombre_titular;
    }

    public String getNumero_tarjeta() {
        return numero_tarjeta;
    }

    public void setNumero_tarjeta(String numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }

    public Date getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(Date fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public String getTipo_tarjeta() {
        return tipo_tarjeta;
    }

    public void setTipo_tarjeta(String tipo_tarjeta) {
        this.tipo_tarjeta = tipo_tarjeta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
