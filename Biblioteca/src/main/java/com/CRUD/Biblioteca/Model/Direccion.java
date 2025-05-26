package com.CRUD.Biblioteca.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "direccion")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String codigo_postal;

    @Column(nullable = false)
    private String direccion;

    public Direccion() {super();}

    public Direccion(Integer id, String pais, String region, String ciudad, String codigo_postal, String direccion, Usuario usuario) {
        this.id = id;
        this.pais = pais;
        this.region = region;
        this.ciudad = ciudad;
        this.codigo_postal = codigo_postal;
        this.direccion = direccion;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
