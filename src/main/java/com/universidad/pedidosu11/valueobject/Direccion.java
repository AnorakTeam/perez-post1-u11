package com.universidad.pedidosu11.valueobject;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Direccion {
    private final String calle;
    private final String ciudad;
    private final String codigoPostal;
    
    // Constructor vacío para JPA
    protected Direccion() {
        this.calle = null;
        this.ciudad = null;
        this.codigoPostal = null;
    }
    
    public Direccion(String calle, String ciudad, String codigoPostal) {
        if (calle == null || calle.isBlank()) 
            throw new IllegalArgumentException("Calle requerida");
        if (ciudad == null || ciudad.isBlank()) 
            throw new IllegalArgumentException("Ciudad requerida");
        if (codigoPostal == null || codigoPostal.isBlank()) 
            throw new IllegalArgumentException("Código postal requerido");
        
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }
    
    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return Objects.equals(calle, direccion.calle) &&
               Objects.equals(ciudad, direccion.ciudad) &&
               Objects.equals(codigoPostal, direccion.codigoPostal);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(calle, ciudad, codigoPostal);
    }
}