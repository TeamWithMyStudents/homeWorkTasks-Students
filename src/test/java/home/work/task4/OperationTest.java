package home.work.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests for Operation Class")
class OperationTest {

    private static final String OPERATION_CLASS_NAME = "home.work.task4.Operation";

    @DisplayName("Ensure 'Operation' class exists")
    @Test
    void testClassExists() {
        try {
            Class<?> clazz = Class.forName(OPERATION_CLASS_NAME);
            assertNotNull(clazz, "Class 'Operation' should exist.");
            assertEquals("Operation", clazz.getSimpleName(),
                    "Class name should be 'Operation'.");
        } catch (ClassNotFoundException e) {
            fail("Class 'Operation' was not found.");
        }
    }

    @DisplayName("Ensure methods in 'Operation' exist with correct parameters")
    @ParameterizedTest(name = "Ensure method '{0}' with parameters {1} exists")
    @MethodSource("provideExpectedMethods")
    void testMethodExists(String methodName, Class<?>[] paramTypes) {
        try {
            Class<?> clazz = Class.forName(OPERATION_CLASS_NAME);
            Method[] methods = clazz.getDeclaredMethods();
            boolean methodExists = Arrays.stream(methods)
                    .anyMatch(method -> method.getName().equals(methodName) &&
                            Arrays.equals(method.getParameterTypes(), paramTypes));

            assertTrue(methodExists,
                    String.format("Method '%s' with parameters %s was not found.",
                            methodName, Arrays.toString(paramTypes)));
        } catch (ClassNotFoundException e) {
            fail("Class 'Operation' was not found.");
        }
    }

    @DisplayName("Ensure return types of 'Operation' methods are correct")
    @ParameterizedTest(name = "Ensure method '{0}' returns {1}")
    @MethodSource("provideReturnTypes")
    void testMethodReturnType(String methodName, Class<?> expectedReturnType) {
        try {
            Class<?> clazz = Class.forName(OPERATION_CLASS_NAME);
            Method method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> m.getName().equals(methodName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError(
                            String.format("Method '%s' was not found.", methodName)));

            assertEquals(expectedReturnType, method.getReturnType(),
                    String.format("Method '%s' should return '%s'.",
                            methodName, expectedReturnType.getSimpleName()));
        } catch (ClassNotFoundException e) {
            fail("Class 'Operation' was not found.");
        }
    }

//    @DisplayName("Ensure 'calculateRectangleArea' throws IllegalArgumentException for invalid input")
//    @ParameterizedTest(name = "Ensure exception for inputs a={0}, b={1}")
//    @MethodSource("provideInvalidInputs")
//    void testCalculateRectangleAreaThrowsException(int a, int b) {
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> Operation.calculateRectangleArea(a, b),
//                "Expected IllegalArgumentException to be thrown for invalid input."
//        );
//        assertEquals("Both arguments should be more than zero",
//                exception.getMessage(),
//                "Exception message does not match the expected one.");
//    }
//
//    @DisplayName("Ensure 'calculateRectangleArea' returns correct area for valid input")
//    @Test
//    void testCalculateRectangleAreaReturnsCorrectValue() {
//        int actual = Operation.calculateRectangleArea(1, 87);
//        int expected = 87;
//        assertEquals(expected, actual,
//                "The area should be 87 for inputs a=1 and b=87.");
//    }
//
//    @DisplayName("Ensure 'tryRectangleArea' returns -1 for invalid input")
//    @ParameterizedTest(name = "Inputs a={0}, b={1} should return -1")
//    @MethodSource("provideInvalidInputs")
//    void testTryRectangleAreaReturnsMinusOne(int a, int b) {
//        int actual = Operation.tryRectangleArea(a, b);
//        int expected = -1;
//        assertEquals(expected, actual,
//                String.format("For inputs a=%d and b=%d, result should be -1.", a, b));
//    }
//
//    @DisplayName("Ensure 'tryRectangleArea' returns correct area for valid input")
//    @Test
//    void testTryRectangleAreaReturnsCorrectValue() {
//        int actual = Operation.tryRectangleArea(3, 5);
//        int expected = 15;
//        assertEquals(expected, actual,
//                "The area should be 15 for inputs a=3 and b=5.");
//    }

    private static Stream<Arguments> provideReturnTypes() {
        return Stream.of(
                Arguments.of("calculateRectangleArea", int.class),
                Arguments.of("tryRectangleArea", int.class)
        );
    }

    private static Stream<Arguments> provideExpectedMethods() {
        return Stream.of(
                Arguments.of("calculateRectangleArea", new Class[]{int.class, int.class}),
                Arguments.of("tryRectangleArea", new Class[]{int.class, int.class})
        );
    }

    private static Stream<Arguments> provideInvalidInputs() {
        return Stream.of(
                Arguments.of(-3, 5),
                Arguments.of(-3, -5),
                Arguments.of(3, -5)
        );
    }
}
