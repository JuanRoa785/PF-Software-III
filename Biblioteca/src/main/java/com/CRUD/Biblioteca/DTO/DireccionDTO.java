package com.CRUD.Biblioteca.DTO;

import com.CRUD.Biblioteca.Model.Direccion;

public class DireccionDTO {
    private Integer id;
    private String pais;
    private String region;
    private String ciudad;
    private String codigo_postal;
    private String direccion;
    private Integer idUsuario;
    private String nombreUsuario;

    public DireccionDTO(Direccion direccion) {
        this.id = direccion.getId();
        this.pais = direccion.getPais();
        this.region = direccion.getRegion();
        this.ciudad = direccion.getCiudad();
        this.codigo_postal = direccion.getCodigo_postal();
        this.direccion = direccion.getDireccion();
        this.idUsuario = direccion.getUsuario().getId();
        this.nombreUsuario = direccion.getUsuario().getNombres(); // opcional
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

