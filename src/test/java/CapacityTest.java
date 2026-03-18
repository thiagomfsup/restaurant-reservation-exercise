import exceptions.OverlapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CapacityTest {

    private LocalDateTime defaultTime;

    @BeforeEach
    void setUp() {
        // Una fecha estándar: Lunes a las 12:00
        defaultTime = LocalDateTime.of(2026, 3, 23, 12, 0);
    }

    // Caso 1: Una sola reserva que intenta entrar y es más grande que el total
    private static Stream<Arguments> providerSingleReservationExceeds() {
        return Stream.of(
                Arguments.of(11, 10, OverlapException.class), // 11 > 10 -> Falla
                Arguments.of(-1, 10, IllegalArgumentException.class) // Tamaño negativo -> Falla
        );
    }

    // Caso 2: Reservas que sumadas superan la capacidad (Solapamiento)
    private static Stream<Arguments> providerOverlapExceeds() {
        return Stream.of(
                // (Capacidad, TamReserva1, TamReserva2, EsperaExcepcion)
                Arguments.of(10, 6, 5, true),  // 6 + 5 = 11 > 10 (Falla)
                Arguments.of(10, 5, 5, false), // 5 + 5 = 10 <= 10 (Pasa)
                Arguments.of(10, 3, 4, false)  // 3 + 4 = 7 <= 10 (Pasa)
        );
    }

    @ParameterizedTest
    @MethodSource("providerSingleReservationExceeds")
    void testSingleReservationCapacity(int size, Class<? extends Throwable> exceptionClass) {
        Restaurant rest = createDefaultRestaurant("R1", 10);

        // El constructor de Reservation según tu archivo:
        // (restaurantId, customerName, phone, partySize, startTime)
        assertThrows(exceptionClass, () -> {
            Reservation res = new Reservation(rest.getId(), "Juan", "123", size, defaultTime);
            rest.reserve(res); // Este método debe estar en tu src/main
        });
    }

    @ParameterizedTest
    @MethodSource("providerOverlapExceeds")
    void testOverlappingCapacity(int capacity, int size1, int size2, boolean shouldFail) {
        Restaurant rest = createDefaultRestaurant("R2", capacity);

        // Primera reserva (ocupa parte del restaurante)
        Reservation res1 = new Reservation(rest.getId(), "Cliente 1", "111", size1, defaultTime);
        rest.reserve(res1);

        // Segunda reserva (misma hora, genera solape)
        Reservation res2 = new Reservation(rest.getId(), "Cliente 2", "222", size2, defaultTime);

        if (shouldFail) {
            assertThrows(OverlapException.class, () -> rest.reserve(res2));
        } else {
            assertDoesNotThrow(() -> rest.reserve(res2));
        }
    }

    // Helper para no repetir la creación del restaurante
    private Restaurant createDefaultRestaurant(String id, int capacity) {
        return new Restaurant(
                id,
                "Restaurante Test",
                LocalTime.of(9, 0),
                capacity,
                LocalTime.of(23, 0),
                Set.of(DayOfWeek.SUNDAY)
        );
    }
}