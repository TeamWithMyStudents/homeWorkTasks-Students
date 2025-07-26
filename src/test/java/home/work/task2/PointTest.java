package home.work.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests for Point Class")
class PointTest {

    private static final String POINT = "home.work.task2.Point";

    @DisplayName("Ensure Point class exists")
    @Test
    void isTypeClassMyUtilsTest() {
        try {
            assertNotNull(Class.forName(POINT), "Point class should exist.");
            assertEquals("Point", Class.forName(POINT).getSimpleName(), "Class name should be 'Point'.");
        } catch (ClassNotFoundException e) {
            fail("Point class does not exist.");
        }
    }

    @DisplayName("Ensure Point class contains 'x' field")
    @Test
    void hasTypeDeclaredFieldTest() {
        try {
            Class<?> clazz = Class.forName(POINT);
            Field field = clazz.getDeclaredField("x");
            assertNotNull(field, "Field 'x' should exist.");
        } catch (ClassNotFoundException e) {
            fail("Point class does not exist.");
        } catch (NoSuchFieldException e) {
            fail("Field 'x' does not exist.");
        }
    }

    @DisplayName("Ensure fields 'x' and 'y' are of type int")
    @ParameterizedTest(name = "Check if field {0} is of type {1}")
    @MethodSource("provideFieldIntData")
    void isCounterFieldIntTest(String fieldName, Class<?> expectedType) {
        try {
            Class<?> clazz = Class.forName(POINT);
            Field field = clazz.getDeclaredField(fieldName);
            assertEquals(expectedType, field.getType(), "Field type mismatch for " + fieldName);
        } catch (ClassNotFoundException e) {
            fail("Point class does not exist.");
        } catch (NoSuchFieldException e) {
            fail("Field '" + fieldName + "' does not exist.");
        }
    }

    @DisplayName("Ensure fields 'x' and 'y' are private and accessible")
    @ParameterizedTest(name = "Check if {0} is private and has value {1}")
    @MethodSource("provideFieldData")
    void isFieldPrivateAndAccessibleTest(String fieldName, int expectedValue) {
        try {
            Class<?> clazz = Class.forName(POINT);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()), "Field '" + fieldName + "' should be private.");

            field.setAccessible(true);
            Constructor<?> constructor = clazz.getDeclaredConstructor(int.class, int.class);
            Object point = constructor.newInstance(3, 7);

            assertEquals(expectedValue, field.getInt(point), "Incorrect value for field '" + fieldName + "'.");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("Ensure Point class has constructor with two int parameters")
    @Test
    void hasConstructorTest() {
        try {
            Class<?> clazz = Class.forName(POINT);
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            assertNotNull(constructors, "No constructors found in Point class.");

            boolean isConstructorFound = Arrays.stream(constructors)
                    .anyMatch(c -> Arrays.equals(c.getParameterTypes(), new Class[]{int.class, int.class}));

            assertTrue(isConstructorFound, "Constructor with two int parameters not found.");
        } catch (ClassNotFoundException e) {
            fail("Point class does not exist.");
        }
    }

    @DisplayName("Ensure Point class contains necessary methods")
    @ParameterizedTest(name = "Check for method {0} with parameters {1}")
    @MethodSource("provideMethodData")
    void hasTypeDeclaredMethod(String methodName, Class<?>[] params) {
        try {
            Class<?> clazz = Class.forName(POINT);
            Method[] methods = clazz.getDeclaredMethods();

            boolean isMethodFound = Arrays.stream(methods)
                    .anyMatch(m -> methodName.equals(m.getName()) && Arrays.equals(m.getParameterTypes(), params));

            assertTrue(isMethodFound, "Method '" + methodName + "' with specified parameters not found.");
        } catch (ClassNotFoundException e) {
            fail("Point class does not exist.");
        }
    }

    @DisplayName("Ensure 'distance' method returns double")
    @Test
    void hasMethodReturnType() {
        try {
            Method[] methods = Class.forName(POINT).getDeclaredMethods();
            Method method = Arrays.stream(methods)
                    .filter(m -> "distance".equals(m.getName()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchMethodException("distance method not found"));

            assertEquals(double.class, method.getReturnType(), "Return type of 'distance' should be double.");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

//    @DisplayName("Calculate distance between two points using coordinates")
//    @ParameterizedTest(name = "Distance between ({0},{1}) and ({2},{3}) should be {4}")
//    @MethodSource("providePointsData")
//    void distanceUsingCoordinatesTest(int x1, int y1, int x2, int y2, double expectedResult) {
//        Point p = new Point(x1, y1);
//        assertEquals(expectedResult, p.distance(x2, y2), 0.001,
//                () -> "Distance calculation failed for points (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ")");
//    }
//
//    @DisplayName("Calculate distance between two Point objects")
//    @ParameterizedTest(name = "Distance between Point({0},{1}) and Point({2},{3}) should be {4}")
//    @MethodSource("providePointsData")
//    void distanceUsingPointObjectTest(int x1, int y1, int x2, int y2, double expectedResult) {
//        Point p1 = new Point(x1, y1);
//        Point p2 = new Point(x2, y2);
//        assertEquals(expectedResult, p1.distance(p2), 0.001,
//                () -> "Distance calculation failed for Point(" + x1 + ", " + y1 + ") and Point(" + x2 + ", " + y2 + ")");
//    }
//
//    @DisplayName("Calculate distance from point to origin (0,0)")
//    @ParameterizedTest(name = "Distance from Point({0},{1}) to origin should be {2}")
//    @MethodSource("providePointZeroData")
//    void distanceZeroTest(int x1, int y1, double expectedResult) {
//        Point p = new Point(x1, y1);
//        assertEquals(expectedResult, p.distance(), 0.001,
//                () -> "Distance to origin failed for Point(" + x1 + ", " + y1 + ")");
//    }

    private static Stream<Arguments> provideFieldData() {
        return Stream.of(Arguments.of("x", 3), Arguments.of("y", 7));
    }

    private static Stream<Arguments> provideMethodData() {
        return Stream.of(Arguments.of("distance", new Class[]{Point.class}),
                Arguments.of("distance", new Class[]{}),
                Arguments.of("getXYPair", new Class[]{}),
                Arguments.of("distance", new Class[]{int.class, int.class}));
    }

    private static Stream<Arguments> providePointsData() {
        return Stream.of(Arguments.of(5, 7, 3, 10, 3.6056),
                Arguments.of(-5, 7, 3, -10, 18.7883),
                Arguments.of(-5, -7, 3, 10, 18.7883),
                Arguments.of(5, -7, -3, 10, 18.7883));
    }

    private static Stream<Arguments> providePointZeroData() {
        return Stream.of(Arguments.of(7, 3, 7.6158),
                Arguments.of(-7, 3, 7.6158),
                Arguments.of(-7, 3, 7.6158),
                Arguments.of(-7, -3, 7.6158));
    }

    private static Stream<Arguments> provideFieldIntData() {
        return Stream.of(
                Arguments.of("x", int.class),
                Arguments.of("y", int.class)
        );
    }
}