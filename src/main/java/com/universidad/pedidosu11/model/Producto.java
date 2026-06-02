package com.universidad.pedidosu11.model;

import jakarta.persistence.*;

@Entity
public class Producto {
    @Id
    private Long id;
    private String nombre;
    private double precio;
    
    // Constructor vacío para JPA
    public Producto() {}
    
    public Producto(Long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
}