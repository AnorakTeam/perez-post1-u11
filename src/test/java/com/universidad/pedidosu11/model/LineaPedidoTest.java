package com.universidad.pedidosu11.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LineaPedido Tests")
class LineaPedidoTest {

    @Test
    @DisplayName("Debe crear línea de pedido correctamente")
    void testCrearLineaPedido() {
        LineaPedido linea = new LineaPedido(1L, 5, 99.99);
        
        assertEquals(1L, linea.getProductoId());
        assertEquals(5, linea.getCantidad());
        assertEquals(99.99, linea.getPrecioUnitario(), 0.01);
    }

    @Test
    @DisplayName("Debe permitir valores en cero")
    void testLineaPedidoCero() {
        LineaPedido linea = new LineaPedido(0L, 0, 0.0);
        
        assertEquals(0L, linea.getProductoId());
        assertEquals(0, linea.getCantidad());
        assertEquals(0.0, linea.getPrecioUnitario(), 0.01);
    }

    @Test
    @DisplayName("Debe permitir valores negativos (aunque no tenga sentido en negocio)")
    void testLineaPedidoNegativo() {
        LineaPedido linea = new LineaPedido(-1L, -5, -10.0);
        
        assertEquals(-1L, linea.getProductoId());
        assertEquals(-5, linea.getCantidad());
        assertEquals(-10.0, linea.getPrecioUnitario(), 0.01);
    }
}