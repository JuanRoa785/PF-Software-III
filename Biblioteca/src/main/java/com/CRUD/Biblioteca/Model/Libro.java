package com.CRUD.Biblioteca.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_genero_literario")
    private GeneroLiterario generoLiterario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 1000)
    private String sinopsis;

    @Column(nullable = false)
    private String id_portada;

    @Column(nullable = false, length = 1000)
    private String portada;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private double impuesto;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String editorial;

    @Column(nullable = false)
    private String edicion;

    @Column(nullable = false)
    private Date fecha_publicacion;

    @Column(nullable = false)
    private String idioma;

    @Column(nullable = false)
    private int numero_paginas;

    private String dimensiones;
    private String coleccion;

    public Libro() {super();}

    public Libro(Integer id, GeneroLiterario generoLiterario, String nombre, String sinopsis, String id_portada, String portada, double precio, double impuesto, int stock, String autor, String editorial, String edicion, Date fecha_publicacion, String idioma, int numero_paginas, String dimensiones, String coleccion) {
        this.id = id;
        this.generoLiterario = generoLiterario;
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.id_portada = id_portada;
        this.portada = portada;
        this.precio = precio;
        this.impuesto = impuesto;
        this.stock = stock;
        this.autor = autor;
        this.editorial = editorial;
        this.edicion = edicion;
        this.fecha_publicacion = fecha_publicacion;
        this.idioma = idioma;
        this.numero_paginas = numero_paginas;
        this.dimensiones = dimensiones;
        this.coleccion = coleccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GeneroLiterario getGeneroLiterario() {
        return generoLiterario;
    }

    public void setGeneroLiterario(GeneroLiterario generoLiterario) {
        this.generoLiterario = generoLiterario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getId_portada() {
        return id_portada;
    }

    public void setId_portada(String id_portada) {
        this.id_portada = id_portada;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumero_paginas() {
        return numero_paginas;
    }

    public void setNumero_paginas(int numero_paginas) {
        this.numero_paginas = numero_paginas;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public String getColeccion() {
        return coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }
}
