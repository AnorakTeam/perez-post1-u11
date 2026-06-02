package com.universidad.pedidosu11.model;

import jakarta.persistence.*;
import com.universidad.pedidosu11.valueobject.DatosCliente;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private DatosCliente cliente;
    
    private double total;
    
    public Pedido() {}
    
    public Pedido(DatosCliente cliente, double total) {
        this.cliente = cliente;
        this.total = total;
    }
    
    public Pedido(Long clienteId, String clienteNombre, double total2) {
        //TODO Auto-generated constructor stub
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DatosCliente getCliente() { return cliente; }
    public void setCliente(DatosCliente cliente) { this.cliente = cliente; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}