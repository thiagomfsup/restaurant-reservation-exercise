import exceptions.OverlapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class CapacityTest {

    private LocalDateTime defaultTime;
    private Restaurant restaurantCap10;
    private Restaurant restaurantCap15;

    @BeforeEach
    void setUp() {
        // Fijamos una fecha y hora por defecto para las reservas de los tests
        defaultTime = LocalDateTime.of(2026, 3, 23, 12, 0);

        // Usamos el constructor real de Restaurant.java
        restaurantCap10 = new Restaurant(
                "R1", "Restaurante 10",
                LocalTime.of(9, 0), 10, LocalTime.of(23, 0),
                Collections.emptySet()
        );

        restaurantCap15 = new Restaurant(
                "R2", "Restaurante 15",
                LocalTime.of(9, 0), 15, LocalTime.of(23, 0),
                Collections.emptySet()
        );
    }

    // PROVIDER 1: Retorna [int, Class]
    private static Stream<Arguments> providerGreaterThanCapacity() {
        return Stream.of(
                Arguments.of(11, OverlapException.class),
                Arguments.of(14, OverlapException.class),
                Arguments.of(-1, IllegalArgumentException.class)
        );
    }

    // TEST 1: Recibe [int, Class]
    @ParameterizedTest
    @MethodSource("providerGreaterThanCapacity")
    void testGreaterThanCapacity(int size, Class<? extends Throwable> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            // El constructor real de Reservation.java recibe 5 parámetros
            Reservation res = new Reservation(
                    restaurantCap10.getId(), "Juan", "555-123", size, defaultTime
            );

            // Suponemos que tienes este método en Restaurant.java
            restaurantCap10.reserve(res);
        });
    }

    // PROVIDER 2: Retorna [int, Class]
    // Valores ajustados para probar el solapamiento:
    // Si la cap es 15 y hacemos 2 reservas del mismo tamaño (ej. 8+8=16), debe fallar la segunda.
    private static Stream<Arguments> providerLessThanCapacity() {
        return Stream.of(
                Arguments.of(8, OverlapException.class),  // 8 + 8 = 16 > 15
                Arguments.of(10, OverlapException.class), // 10 + 10 = 20 > 15
                Arguments.of(11, OverlapException.class)  // 11 + 11 = 22 > 15
        );
    }

    // TEST 2: Recibe [int, Class]
    @ParameterizedTest
    @MethodSource("providerLessThanCapacity")
    void testLessThanCapacity(int size, Class<? extends Throwable> exceptionClass) {
        Reservation reser1 = new Reservation(
                restaurantCap15.getId(), "Cliente 1", "111", size, defaultTime
        );
        Reservation reser2 = new Reservation(
                restaurantCap15.getId(), "Cliente 2", "222", size, defaultTime
        );

        // 1. La primera reserva DEBE funcionar (porque size < 15)
        try {
            restaurantCap15.reserve(reser1);
        } catch (Exception e) {
            fail("La primera reserva no debería haber lanzado excepción, pero lanzó: " + e.getMessage());
        }

        // 2. La segunda reserva DEBE fallar porque al sumarse solapan y superan 15
        assertThrows(exceptionClass, () -> {
            restaurantCap15.reserve(reser2);
        });
    }
}