package home.work.task3;

import static org.junit.jupiter.api.Assertions.*;

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

@DisplayName("Unit Tests for Product Class")
class ProductTest {

    private static final String PRODUCT = "home.work.task3.Product";

    @DisplayName("Ensure Product class exists")
    @Test
    void isTypeClass() {
        try {
            assertNotNull(Class.forName(PRODUCT), "Product class should exist");
            assertEquals("Product", Class.forName(PRODUCT).getSimpleName(), "Class name should be 'Product'");
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        }
    }

    @DisplayName("Ensure Product class has a constructor")
    @Test
    void hasProductClassConstructorTest() {
        try {
            Class<?> clazz = Class.forName(PRODUCT);
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            assertTrue(constructors.length > 0, "Product class should have at least one constructor");
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        }
    }

    @DisplayName("Ensure Product class has a public constructor")
    @Test
    void hasProductClassPublicConstructorTest() {
        try {
            Class<?> clazz = Class.forName(PRODUCT);
            boolean hasPublicConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                    .anyMatch(constructor -> Modifier.isPublic(constructor.getModifiers()));

            assertTrue(hasPublicConstructor, "Product class should have a public constructor");
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        }
    }

    @DisplayName("Ensure fields are present and private")
    @ParameterizedTest(name = "Field {0} should be private")
    @MethodSource("provideDataForMethod")
    void isFieldPrivateTest(String fieldName) {
        try {
            Class<?> clazz = Class.forName(PRODUCT);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()), "Field '" + fieldName + "' should be private");
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        } catch (NoSuchFieldException e) {
            fail("Field '" + fieldName + "' not found in Product class");
        }
    }

    @DisplayName("Ensure field types are correct")
    @ParameterizedTest(name = "Field {0} should be of type {1}")
    @MethodSource("provideDataForMethod")
    void hasFieldType(String fieldName, Class<?> expectedType) {
        try {
            Class<?> clazz = Class.forName(PRODUCT);
            Field field = clazz.getDeclaredField(fieldName);
            assertEquals(expectedType, field.getType(), "Field '" + fieldName + "' should be of type " + expectedType.getSimpleName());
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        } catch (NoSuchFieldException e) {
            fail("Field '" + fieldName + "' not found in Product class");
        }
    }

    @DisplayName("Ensure methods return correct types")
    @ParameterizedTest(name = "Method {0} should return {1}")
    @MethodSource("provideDataForReturnTypeMethod")
    void hasMethodReturnType(String methodName, Class<?> expectedReturnType) {
        try {
            Class<?>[] params = getMethodParams(methodName);

            Method method = Class.forName(PRODUCT).getDeclaredMethod(methodName, params);
            assertEquals(expectedReturnType, method.getReturnType(),
                    "Method '" + methodName + "' should return " + expectedReturnType.getSimpleName());

        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        } catch (NoSuchMethodException e) {
            fail("Method '" + methodName + "' not found in Product class");
        }
    }

    @DisplayName("Ensure methods with parameters are present")
    @ParameterizedTest(name = "Method {0} with parameters should exist")
    @MethodSource("provideDataMethod")
    void hasTypeDeclaredMethod(String methodName, Class<?>[] params) {
        try {
            Method method = Class.forName(PRODUCT).getDeclaredMethod(methodName, params);
            assertNotNull(method, "Method '" + methodName + "' with specified parameters should be present in Product class");
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        } catch (NoSuchMethodException e) {
            fail("Method '" + methodName + "' with specified parameters not found in Product class");
        }
    }

    @DisplayName("Ensure 'count' method is static")
    @Test
    void isCountMethodStatic() {
        try {
            Method method = Class.forName(PRODUCT).getDeclaredMethod("count");
            assertTrue(Modifier.isStatic(method.getModifiers()), "'count' method should be static");
        } catch (ClassNotFoundException e) {
            fail("Product class not found");
        } catch (NoSuchMethodException e) {
            fail("'count' method not found in Product class");
        }
    }

//    @DisplayName("Ensure 'count' method returns correct product count")
//    @Test
//    void countOfCreatedProductsTest() {
//        new Product();
//        new Product("Car", 15000);
//        assertEquals(2, Product.count(), "The count method should return the correct number of created products");
//    }

    private static Stream<Arguments> provideDataForMethod() {
        return Stream.of(
                Arguments.of("name", String.class),
                Arguments.of("price", double.class)
        );
    }

    private static Stream<Arguments> provideDataForReturnTypeMethod() {
        return Stream.of(
                Arguments.of("setPrice", void.class),
                Arguments.of("count", int.class),
                Arguments.of("getPrice", double.class),
                Arguments.of("getName", String.class),
                Arguments.of("setName", void.class)
        );
    }

    private static Stream<Arguments> provideDataMethod() {
        return Stream.of(
                Arguments.of("setName", new Class[]{String.class}),
                Arguments.of("setPrice", new Class[]{double.class}),
                Arguments.of("getName", new Class[]{}),
                Arguments.of("count", new Class[]{}),
                Arguments.of("getPrice", new Class[]{})
        );
    }

    private Class<?>[] getMethodParams(String methodName) {
        return switch (methodName) {
            case "setName" -> new Class[]{String.class};
            case "setPrice" -> new Class[]{double.class};
            default -> new Class[]{};
        };
    }
}
