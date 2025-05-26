package com.CRUD.Biblioteca.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Column(nullable = false, length = 250, name = "correo", unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String contrasena;

    @Column(nullable = false, length = 150)
    private String nombres;

    @Column(nullable = false, length = 150)
    private String apellidos;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false)
    private boolean cuenta_activa = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;

    public Usuario() {super();}

    public Usuario(Integer id, TipoUsuario tipoUsuario, String email, String contrasena, String nombres, String apellidos, String telefono, boolean cuenta_activa, List<Token> tokens) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.cuenta_activa = cuenta_activa;
        this.tokens = tokens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isCuenta_activa() {
        return cuenta_activa;
    }

    public void setCuenta_activa(boolean cuenta_activa) {
        this.cuenta_activa = cuenta_activa;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
