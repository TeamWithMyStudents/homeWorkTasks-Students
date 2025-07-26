package home.work.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Circle Class Unit Tests")
class CircleTest {

    private static final String CIRCLE = "home.work.task1.Circle";

    @DisplayName("Check if Circle class is present")
    @Test
    void isTypeClassCircle() {
        try {
            Class<?> clazz = Class.forName(CIRCLE);
            assertNotNull(clazz, "Circle class should exist.");
            assertEquals("Circle", clazz.getSimpleName(), "Class name should be 'Circle'.");
        } catch (ClassNotFoundException e) {
            fail("Circle class is missing.");
        }
    }

    @DisplayName("Verify Circle class has various draw() methods")
    @ParameterizedTest(name = "Checking method: {1} with parameters {2}")
    @MethodSource("provideDataCircleClass")
    void hasTypeDeclaredMethod(String className, String methodName, Class<?>[] params) {
        try {
            Method method = Class.forName(className).getDeclaredMethod(methodName, params);
            assertNotNull(method, "Method " + methodName + " should exist.");
        } catch (NoSuchMethodException e) {
            fail("Method '" + methodName + "' with parameters " + Arrays.toString(params) + " not found.");
        } catch (ClassNotFoundException e) {
            fail("Circle class not found.");
        }
    }

    @DisplayName("Check that draw() methods in Circle class are public")
    @ParameterizedTest(name = "Checking if {1} is public with parameters {2}")
    @MethodSource("provideDataCircleClass")
    void hasPublicDrawMethod(String className, String methodName, Class<?>[] params) {
        try {
            Method method = Class.forName(className).getMethod(methodName, params);
            assertTrue(Modifier.isPublic(method.getModifiers()),
                    "Method " + methodName + " should be public.");
        } catch (NoSuchMethodException e) {
            fail("Public method '" + methodName + "' with parameters " + Arrays.toString(params) + " not found.");
        } catch (ClassNotFoundException e) {
            fail("Circle class not found.");
        }
    }

    @DisplayName("Ensure non-existing method throws exception")
    @Test
    void methodNotExist() {
        Executable executable = () -> Class.forName(CIRCLE).getDeclaredMethod("nonExistingMethod");
        assertThrows(NoSuchMethodException.class, executable,
                "Expected NoSuchMethodException when method does not exist.");
    }

    private static Stream<Arguments> provideDataCircleClass() {
        return Stream.of(
                Arguments.of(CIRCLE, "draw", new Class[]{}),
                Arguments.of(CIRCLE, "draw", new Class[]{String.class}),
                Arguments.of(CIRCLE, "draw", new Class[]{float.class}),
                Arguments.of(CIRCLE, "draw", new Class[]{String.class, float.class})
        );
    }
}