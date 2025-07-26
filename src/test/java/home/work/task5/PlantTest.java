package home.work.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests for Plant, Color, Type, and Exception Classes")
class PlantTest {

    private static final String PLANT = "home.work.task5.Plant";
    private static final String COLOR_ENUM = "home.work.task5.Color";
    private static final String TYPE_ENUM = "home.work.task5.Type";
    private static final String COLOR_EXCEPTION = "home.work.task5.ColorException";
    private static final String TYPE_EXCEPTION = "home.work.task5.TypeException";

    @DisplayName("Ensure classes exist")
    @ParameterizedTest(name = "Ensure class {0} exists")
    @MethodSource("provideClasses")
    void testClassExists(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            assertNotNull(clazz, "Class '" + className + "' should exist.");
            assertEquals(className.substring(className.lastIndexOf('.') + 1), clazz.getSimpleName(),
                    "Class name mismatch.");
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' does not exist.");
        }
    }

    private static Stream<Arguments> provideClasses() {
        return Stream.of(
                Arguments.of(PLANT),
                Arguments.of(TYPE_EXCEPTION),
                Arguments.of(COLOR_EXCEPTION)
        );
    }

    @DisplayName("Ensure exception classes extend Exception")
    @ParameterizedTest(name = "Ensure {0} extends Exception")
    @MethodSource("provideExceptionClasses")
    void testExceptionClassInheritance(String exceptionClassName) {
        try {
            Class<?> parentClass = Class.forName("java.lang.Exception");
            Class<?> exceptionClass = Class.forName(exceptionClassName);
            assertTrue(parentClass.isAssignableFrom(exceptionClass),
                    "Class '" + exceptionClassName + "' should extend Exception.");
        } catch (ClassNotFoundException e) {
            fail("Class '" + exceptionClassName + "' not found.");
        }
    }

    private static Stream<Arguments> provideExceptionClasses() {
        return Stream.of(
                Arguments.of(TYPE_EXCEPTION),
                Arguments.of(COLOR_EXCEPTION)
        );
    }

    @DisplayName("Ensure enums exist")
    @ParameterizedTest(name = "Ensure enum {0} exists")
    @MethodSource("provideEnums")
    void testEnumExists(String enumName) {
        try {
            Class<?> clazz = Class.forName(enumName);
            assertTrue(clazz.isEnum(), "Class '" + enumName + "' should be an enum.");
        } catch (ClassNotFoundException e) {
            fail("Enum '" + enumName + "' does not exist.");
        }
    }

    private static Stream<Arguments> provideEnums() {
        return Stream.of(
                Arguments.of(COLOR_ENUM),
                Arguments.of(TYPE_ENUM)
        );
    }

    @DisplayName("Ensure fields exist in classes")
    @ParameterizedTest(name = "Ensure class {0} contains field {1}")
    @MethodSource("provideFields")
    void testFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = Arrays.stream(clazz.getDeclaredFields())
                    .filter(f -> f.getName().equals(fieldName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Field '" + fieldName + "' not found."));
            assertNotNull(field, "Field '" + fieldName + "' should exist in class '" + className + "'.");
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' does not exist.");
        }
    }

    private static Stream<Arguments> provideFields() {
        return Stream.of(
                Arguments.of(COLOR_ENUM, "WHITE"),
                Arguments.of(COLOR_ENUM, "RED"),
                Arguments.of(COLOR_ENUM, "BLUE"),
                Arguments.of(TYPE_ENUM, "RARE"),
                Arguments.of(TYPE_ENUM, "ORDINARY"),
                Arguments.of(PLANT, "name"),
                Arguments.of(PLANT, "color"),
                Arguments.of(PLANT, "type")
        );
    }

    @DisplayName("Ensure constructors exist")
    @ParameterizedTest(name = "Ensure class {0} has constructor with parameters {1}")
    @MethodSource("provideConstructors")
    void testConstructorExists(String className, Class<?>[] paramTypes) {
        try {
            Class<?> clazz = Class.forName(className);
            boolean constructorExists = Arrays.stream(clazz.getDeclaredConstructors())
                    .anyMatch(c -> Arrays.equals(c.getParameterTypes(), paramTypes));
            assertTrue(constructorExists,
                    "Constructor with parameters " + Arrays.toString(paramTypes) + " should exist in class '" + className + "'.");
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' does not exist.");
        }
    }

    private static Stream<Arguments> provideConstructors() {
        return Stream.of(
                Arguments.of(COLOR_EXCEPTION, new Class[]{String.class}),
                Arguments.of(TYPE_EXCEPTION, new Class[]{String.class}),
                Arguments.of(PLANT, new Class[]{String.class, String.class, String.class})
        );
    }

    @DisplayName("Ensure fields have correct types")
    @ParameterizedTest(name = "Ensure field {1} in {0} is of type {2}")
    @MethodSource("provideFieldTypes")
    void testFieldType(String className, String fieldName, Class<?> expectedType) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = Arrays.stream(clazz.getDeclaredFields())
                    .filter(f -> f.getName().equals(fieldName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Field '" + fieldName + "' not found."));
            assertEquals(expectedType, field.getType(),
                    "Field '" + fieldName + "' should be of type '" + expectedType.getSimpleName() + "'.");
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' does not exist.");
        }
    }

    @DisplayName("Ensure 'toString' method is overridden in Plant class")
    @Test
    void testToStringOverride() {
        try {
            Class<?> clazz = Class.forName(PLANT);
            Method toStringMethod = clazz.getDeclaredMethod("toString");
            assertNotNull(toStringMethod, "'toString' method should be overridden in 'Plant' class.");
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            fail("'toString' method is not overridden in 'Plant' class.");
        }
    }

    private static Stream<Arguments> provideFieldTypes() throws ClassNotFoundException {
        return Stream.of(
                Arguments.of(PLANT, "name", String.class),
                Arguments.of(PLANT, "color", Class.forName(COLOR_ENUM)),
                Arguments.of(PLANT, "type", Class.forName(TYPE_ENUM))
        );
    }

//    @DisplayName("Ensure valid Plant object is created without exceptions")
//    @Test
//    void testValidPlantCreation() {
//        try {
//            for (Color color : Color.values()) {
//                for (Type type : Type.values()) {
//                    assertNotNull(new Plant(type.toString(), color.toString(), "MyPlant"),
//                            "Failed to create a valid Plant object.");
//                }
//            }
//        } catch (TypeException | ColorException e) {
//            fail("Valid Plant object creation failed.");
//        }
//    }

//    @DisplayName("Ensure exception is thrown for invalid Plant parameters")
//    @ParameterizedTest(name = "Ensure {0} is thrown for type={3}, color={2}, name={1}")
//    @MethodSource("provideInvalidPlantParameters")
//    void testInvalidPlantCreation(Class<? extends Exception> exceptionClass, String name, String color, String type) {
//        assertThrows(exceptionClass, () -> new Plant(type, color, name),
//                "Expected exception not thrown for invalid Plant parameters.");
//    }
//
//    private static Stream<Arguments> provideInvalidPlantParameters() {
//        return Stream.of(
//                Arguments.of(TypeException.class, "someType", "blue", "NewType"),
//                Arguments.of(ColorException.class, "someType", "NewColor", "rare")
//        );
//    }
//
//    @DisplayName("Ensure 'toString' method returns the correct format")
//    @Test
//    void testToStringMethod() throws ColorException, TypeException {
//        Plant plant = new Plant("Ordinary", "White", "MyPlant");
//        String expected = "{type: ORDINARY, color: WHITE, name: MyPlant}";
//        assertEquals(expected, plant.toString(),
//                "'toString' method output does not match the expected format.");
//    }
//
//    @DisplayName("Ensure getters return correct values")
//    @Test
//    void testGettersReturnCorrectValues() throws TypeException, ColorException {
//        Plant plant = new Plant("ordinary", "white", "Tulip");
//        assertEquals("Tulip", plant.getName(), "Name should be 'Tulip'");
//        assertEquals(Color.WHITE, plant.getColor(), "Color should be WHITE");
//        assertEquals(Type.ORDINARY, plant.getType(), "Type should be ORDINARY");
//    }
//
//    @DisplayName("Ensure Plant can be created with null or empty name")
//    @ParameterizedTest
//    @MethodSource("provideNames")
//    void testPlantWithEmptyOrNullName(String name) throws TypeException, ColorException {
//        Plant plant = new Plant("rare", "red", name);
//        assertNotNull(plant, "Plant should be created even with empty or null name");
//        assertEquals(name, plant.getName(), "Name should match the input value");
//    }
//
//    private static Stream<Arguments> provideNames() {
//        return Stream.of(
//                Arguments.of(""),
//                Arguments.of((String) null)
//        );
//    }
//
//    @DisplayName("Ensure toString() returns correct format for different inputs")
//    @ParameterizedTest
//    @MethodSource("providePlants")
//    void testToStringReturnsCorrectFormat(String type, String color, String name, String expected) throws TypeException, ColorException {
//        Plant plant = new Plant(type, color, name);
//        assertEquals(expected, plant.toString(), "toString() output does not match the expected format");
//    }

    private static Stream<Arguments> providePlants() {
        return Stream.of(
                Arguments.of("rare", "red", "Rose", "{type: RARE, color: RED, name: Rose}"),
                Arguments.of("ordinary", "blue", "Cornflower", "{type: ORDINARY, color: BLUE, name: Cornflower}"),
                Arguments.of("ordinary", "white", "Lily", "{type: ORDINARY, color: WHITE, name: Lily}")
        );
    }

//    @DisplayName("Ensure exception is thrown for invalid color or type")
//    @ParameterizedTest
//    @MethodSource("provideInvalidParameters")
//    void testInvalidColorOrTypeThrowsException(String type, String color, Class<? extends Exception> expectedException) {
//        assertThrows(expectedException, () -> new Plant(type, color, "Daisy"),
//                "Expected exception was not thrown for invalid color or type");
//    }
//
//    private static Stream<Arguments> provideInvalidParameters() {
//        return Stream.of(
//                Arguments.of("invalidType", "red", TypeException.class),
//                Arguments.of("rare", "invalidColor", ColorException.class),
//                Arguments.of("invalidType", "invalidColor", TypeException.class)
//        );
//    }
//
//    @DisplayName("Ensure different objects are not equal")
//    @Test
//    void testDifferentPlantsAreNotEqual() throws TypeException, ColorException {
//        Plant plant1 = new Plant("rare", "red", "Rose");
//        Plant plant2 = new Plant("ordinary", "blue", "Cornflower");
//        assertNotEquals(plant1.toString(), plant2.toString(), "Plants with different parameters should not be equal");
//    }
//
//    @DisplayName("Ensure same parameters create equal toString output")
//    @Test
//    void testEqualPlantsHaveSameToString() throws TypeException, ColorException {
//        Plant plant1 = new Plant("rare", "red", "Rose");
//        Plant plant2 = new Plant("rare", "red", "Rose");
//        assertEquals(plant1.toString(), plant2.toString(), "Plants with the same parameters should have equal toString output");
//    }
}
