package com.CRUD.Biblioteca.DTO;

import com.CRUD.Biblioteca.Model.TipoUsuario;
import com.CRUD.Biblioteca.Model.Usuario;

public class UsuarioDTO {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private boolean cuenta_activa;
    private TipoUsuario tipoUsuario;
    // No incluimos tokens ni contraseña

    public UsuarioDTO(Usuario user) {
        this.id = user.getId();
        this.nombres = user.getNombres();
        this.apellidos = user.getApellidos();
        this.email = user.getEmail();
        this.telefono = user.getTelefono();
        this.cuenta_activa = user.isCuenta_activa();
        this.tipoUsuario = user.getTipoUsuario();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
