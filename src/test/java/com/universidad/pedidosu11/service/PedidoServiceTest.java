package com.universidad.pedidosu11.service;

import com.universidad.pedidosu11.model.LineaPedido;
import com.universidad.pedidosu11.model.Pedido;
import com.universidad.pedidosu11.repository.PedidoRepository;
import com.universidad.pedidosu11.valueobject.CodigoDescuento;
import com.universidad.pedidosu11.valueobject.DatosCliente;
import com.universidad.pedidosu11.valueobject.Direccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService Tests")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private PedidoService pedidoService;

    private DatosCliente clienteValido;
    private LineaPedido[] lineasValidas;
    private Direccion direccionValida;

    @BeforeEach
    void setUp() {
        direccionValida = new Direccion("Calle 123", "Bogotá", "110111");
        clienteValido = new DatosCliente(
            "Juan Pérez",
            "juan@email.com",
            "3001234567",
            direccionValida
        );

        lineasValidas = new LineaPedido[]{
            new LineaPedido(1L, 2, 100.0),
            new LineaPedido(2L, 1, 50.0)
        };
    }

    @Nested
    @DisplayName("Tests de procesarPedido - Casos exitosos")
    class ProcesarPedidoExitoso {

        @Test
        @DisplayName("Debe procesar pedido sin descuento correctamente")
        void testProcesarPedidoSinDescuento() {
            // Arrange
            Pedido pedidoGuardado = new Pedido(clienteValido, 250.0);
            pedidoGuardado.setId(1L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            // Act
            String resultado = pedidoService.procesarPedido(
                clienteValido, lineasValidas, false, null
            );

            // Assert
            assertTrue(resultado.startsWith("OK_"));
            assertEquals("OK_1", resultado);
            
            ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
            verify(pedidoRepository).save(pedidoCaptor.capture());
            
            Pedido pedidoEnviado = pedidoCaptor.getValue();
            assertEquals(clienteValido, pedidoEnviado.getCliente());
            assertEquals(250.0, pedidoEnviado.getTotal(), 0.01);
            
            verify(notificationService).notificarPedido(clienteValido, false);
        }

        @Test
        @DisplayName("Debe aplicar descuento VIP10 correctamente")
        void testProcesarPedidoConDescuentoVIP10() {
            // Arrange
            CodigoDescuento descuento = new CodigoDescuento("VIP10");
            Pedido pedidoGuardado = new Pedido(clienteValido, 225.0);
            pedidoGuardado.setId(2L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            // Act
            String resultado = pedidoService.procesarPedido(
                clienteValido, lineasValidas, true, descuento
            );

            // Assert
            assertEquals("OK_2", resultado);
            
            ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
            verify(pedidoRepository).save(pedidoCaptor.capture());
            
            // Total original: 250 - 10% = 225
            assertEquals(225.0, pedidoCaptor.getValue().getTotal(), 0.01);
            verify(notificationService).notificarPedido(clienteValido, true);
        }

        @Test
        @DisplayName("Debe aplicar descuento NEW20 correctamente")
        void testProcesarPedidoConDescuentoNEW20() {
            // Arrange
            CodigoDescuento descuento = new CodigoDescuento("NEW20");
            Pedido pedidoGuardado = new Pedido(clienteValido, 200.0);
            pedidoGuardado.setId(3L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            // Act
            String resultado = pedidoService.procesarPedido(
                clienteValido, lineasValidas, false, descuento
            );

            // Assert
            assertEquals("OK_3", resultado);
            
            ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
            verify(pedidoRepository).save(pedidoCaptor.capture());
            
            // Total original: 250 - 20% = 200
            assertEquals(200.0, pedidoCaptor.getValue().getTotal(), 0.01);
        }

        @Test
        @DisplayName("Debe manejar lista vacía de productos")
        void testProcesarPedidoConLineasVacias() {
            // Arrange
            LineaPedido[] lineasVacias = new LineaPedido[0];
            Pedido pedidoGuardado = new Pedido(clienteValido, 0.0);
            pedidoGuardado.setId(4L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            // Act
            String resultado = pedidoService.procesarPedido(
                clienteValido, lineasVacias, false, null
            );

            // Assert
            assertEquals("OK_4", resultado);
            
            ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
            verify(pedidoRepository).save(pedidoCaptor.capture());
            assertEquals(0.0, pedidoCaptor.getValue().getTotal(), 0.01);
        }

        @Test
        @DisplayName("Debe manejar código de descuento inválido como sin descuento")
        void testProcesarPedidoConDescuentoInvalido() {
            // Arrange
            CodigoDescuento descuentoInvalido = new CodigoDescuento("INVALIDO");
            Pedido pedidoGuardado = new Pedido(clienteValido, 250.0);
            pedidoGuardado.setId(5L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            // Act
            String resultado = pedidoService.procesarPedido(
                clienteValido, lineasValidas, false, descuentoInvalido
            );

            // Assert
            assertEquals("OK_5", resultado);
            
            ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
            verify(pedidoRepository).save(pedidoCaptor.capture());
            assertEquals(250.0, pedidoCaptor.getValue().getTotal(), 0.01);
        }
    }

    @Nested
    @DisplayName("Tests de cálculo de total")
    class CalculoTotalTests {

        @Test
        @DisplayName("Debe calcular total correctamente con múltiples líneas")
        void testCalcularTotalMultipleLineas() {
            // Use reflection or make method package-private for testing
            // Alternative: test through public method
            Pedido pedidoGuardado = new Pedido(clienteValido, 250.0);
            pedidoGuardado.setId(1L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            pedidoService.procesarPedido(clienteValido, lineasValidas, false, null);

            verify(pedidoRepository).save(argThat(pedido -> 
                Math.abs(pedido.getTotal() - 250.0) < 0.01
            ));
        }

        @Test
        @DisplayName("Debe calcular total correctamente con una sola línea")
        void testCalcularTotalUnaLinea() {
            LineaPedido[] unaLinea = new LineaPedido[]{
                new LineaPedido(1L, 3, 25.0)
            };
            
            Pedido pedidoGuardado = new Pedido(clienteValido, 75.0);
            pedidoGuardado.setId(1L);
            when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

            pedidoService.procesarPedido(clienteValido, unaLinea, false, null);

            verify(pedidoRepository).save(argThat(pedido -> 
                Math.abs(pedido.getTotal() - 75.0) < 0.01
            ));
        }
    }
}