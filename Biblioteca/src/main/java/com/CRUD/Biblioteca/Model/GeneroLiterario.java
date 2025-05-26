package com.CRUD.Biblioteca.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "genero_literario")
public class GeneroLiterario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String portada;

    private String descripcion;

    public GeneroLiterario() {super();}

    public GeneroLiterario(Integer id, String nombre, String descripcion, String portada) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portada = portada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
