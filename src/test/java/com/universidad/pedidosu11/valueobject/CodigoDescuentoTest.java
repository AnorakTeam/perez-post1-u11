package com.universidad.pedidosu11.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CodigoDescuento Value Object Tests")
class CodigoDescuentoTest {

    @Test
    @DisplayName("Debe crear código de descuento nulo válido")
    void testCodigoNulo() {
        CodigoDescuento descuento = new CodigoDescuento(null);
        
        assertNull(descuento.getCodigo());
        assertEquals(0.0, descuento.getPorcentaje());
        assertFalse(descuento.esValido());
    }

    @Test
    @DisplayName("Debe reconocer VIP10 correctamente")
    void testCodigoVIP10() {
        CodigoDescuento descuento = new CodigoDescuento("VIP10");
        
        assertEquals("VIP10", descuento.getCodigo());
        assertEquals(0.10, descuento.getPorcentaje());
        assertTrue(descuento.esValido());
    }

    @Test
    @DisplayName("Debe reconocer NEW20 correctamente")
    void testCodigoNEW20() {
        CodigoDescuento descuento = new CodigoDescuento("NEW20");
        
        assertEquals("NEW20", descuento.getCodigo());
        assertEquals(0.20, descuento.getPorcentaje());
        assertTrue(descuento.esValido());
    }

    @ParameterizedTest
    @ValueSource(strings = {"INVALIDO", "VIP", "DESC10", "", " ", "VIP10 "})
    @DisplayName("Códigos inválidos deben tener porcentaje 0")
    void testCodigoInvalido(String codigoInvalido) {
        CodigoDescuento descuento = new CodigoDescuento(codigoInvalido);
        
        assertEquals(codigoInvalido, descuento.getCodigo());
        assertEquals(0.0, descuento.getPorcentaje());
        assertFalse(descuento.esValido());
    }

    @Test
    @DisplayName("Debe preservar case sensitivity exacta")
    void testCaseSensitive() {
        CodigoDescuento descuentoMayus = new CodigoDescuento("VIP10");
        CodigoDescuento descuentoMinus = new CodigoDescuento("vip10");
        
        assertTrue(descuentoMayus.esValido());
        assertFalse(descuentoMinus.esValido());
    }

    @Test
    @DisplayName("Código vacío debe ser inválido")
    void testCodigoVacio() {
        CodigoDescuento descuento = new CodigoDescuento("");
        
        assertEquals("", descuento.getCodigo());
        assertEquals(0.0, descuento.getPorcentaje());
        assertFalse(descuento.esValido());
    }
}