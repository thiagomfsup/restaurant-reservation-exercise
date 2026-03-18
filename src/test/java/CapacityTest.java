import exceptions.OverlapException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CapacityTest {

    private static List<Arguments> providerGreaterThanCapacity() {
        return List.of(
                Arguments.of(11, OverlapException.class),
                Arguments.of(14, OverlapException.class),
                Arguments.of(-1, IllegalArgumentException.class)
        );
    }

    private static List<Arguments> providerLessThanCapacity() {
        return List.of(
                Arguments.of(11, OverlapException.class),
                Arguments.of(15, OverlapException.class),
                Arguments.of(16, OverlapException.class)
        );
    }


    @ParameterizedTest
    @MethodSource("providerGreaterThanCapacity")
    void testGreaterThanCapacity(int size, Class<? extends Throwable> exceptionClass) {
        Restaurant rest = new Restaurant(10);
        assertThrows(exceptionClass, () -> rest.reserve(new Reservation(rest, size)));
    }

    @ParameterizedTest
    @MethodSource("providerLessThanCapacity")
    void testLessThanCapacity(int size,  Class<? extends Throwable> exceptionClass) {

        Restaurant rest = new Restaurant(15);
        Reservation reser = new Reservation(rest, size);
        Reservation reser2 = new Reservation(rest, size);

        rest.reserve(reser);
        assertThrows(exceptionClass, () -> rest.reserve(reser2));


    }





}
