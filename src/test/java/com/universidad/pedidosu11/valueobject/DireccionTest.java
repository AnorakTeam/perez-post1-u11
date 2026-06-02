package com.universidad.pedidosu11.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Direccion Value Object Tests")
class DireccionTest {

    @Test
    @DisplayName("Debe crear dirección válida correctamente")
    void testCrearDireccionValida() {
        Direccion direccion = new Direccion("Calle Falsa 123", "Bogotá", "110111");

        assertAll("Propiedades de dirección",
            () -> assertEquals("Calle Falsa 123", direccion.getCalle()),
            () -> assertEquals("Bogotá", direccion.getCiudad()),
            () -> assertEquals("110111", direccion.getCodigoPostal())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("Debe lanzar excepción cuando la calle es inválida")
    void testCalleInvalida(String calleInvalida) {
        assertThrows(IllegalArgumentException.class, () ->
            new Direccion(calleInvalida, "Bogotá", "110111")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    @DisplayName("Debe lanzar excepción cuando la ciudad es inválida")
    void testCiudadInvalida(String ciudadInvalida) {
        assertThrows(IllegalArgumentException.class, () ->
            new Direccion("Calle 123", ciudadInvalida, "110111")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    @DisplayName("Debe lanzar excepción cuando el código postal es inválido")
    void testCodigoPostalInvalido(String codigoPostalInvalido) {
        assertThrows(IllegalArgumentException.class, () ->
            new Direccion("Calle 123", "Bogotá", codigoPostalInvalido)
        );
    }

    @Test
    @DisplayName("Dos direcciones iguales deben ser equivalentes")
    void testEquals() {
        Direccion dir1 = new Direccion("Calle A", "Ciudad A", "12345");
        Direccion dir2 = new Direccion("Calle A", "Ciudad A", "12345");

        assertEquals(dir1, dir2);
        assertEquals(dir1.hashCode(), dir2.hashCode());
    }

    @Test
    @DisplayName("Direcciones diferentes no deben ser iguales")
    void testNotEquals() {
        Direccion dir1 = new Direccion("Calle A", "Ciudad A", "12345");
        Direccion dir2 = new Direccion("Calle B", "Ciudad B", "67890");

        assertNotEquals(dir1, dir2);
    }
}