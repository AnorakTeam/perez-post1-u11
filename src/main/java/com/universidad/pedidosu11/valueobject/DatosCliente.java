package com.universidad.pedidosu11.valueobject;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DatosCliente {
    private final String nombre;
    private final String email;
    private final String telefono;
    private final Direccion direccion;
    
    protected DatosCliente() {
        this.nombre = null;
        this.email = null;
        this.telefono = null;
        this.direccion = null;
    }
    
    public DatosCliente(String nombre, String email, String telefono, Direccion direccion) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre requerido");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");
        if (telefono == null || telefono.isBlank())
            throw new IllegalArgumentException("Teléfono requerido");
        if (direccion == null)
            throw new IllegalArgumentException("Dirección requerida");
        
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public Direccion getDireccion() { return direccion; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatosCliente that = (DatosCliente) o;
        return Objects.equals(nombre, that.nombre) &&
               Objects.equals(email, that.email) &&
               Objects.equals(telefono, that.telefono) &&
               Objects.equals(direccion, that.direccion);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nombre, email, telefono, direccion);
    }
}