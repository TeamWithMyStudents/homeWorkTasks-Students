package home.work.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InsufficientAmountExceptionTest {

    private static final String CLASS_NAME = "home.work.task7.InsufficientAmountException";

    private Class<?> getExceptionClass() {
        try {
            return Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            fail("Class 'InsufficientAmountException' not found. Ensure it is in package 'home.work.task7'.");
            return null;
        }
    }

    @DisplayName("Check if 'InsufficientAmountException' class exists")
    @Test
    void testClassExists() {
        Class<?> clazz = getExceptionClass();
        assertNotNull(clazz, "Class should not be null.");
        assertEquals("InsufficientAmountException", clazz.getSimpleName(),
                "Class name should be 'InsufficientAmountException'.");
    }

    @DisplayName("Check if 'InsufficientAmountException' extends 'Exception'")
    @Test
    void testClassExtendsException() {
        Class<?> clazz = getExceptionClass();
        assertNotNull(clazz, "Class should not be null.");
        assertTrue(Exception.class.isAssignableFrom(clazz),
                "'InsufficientAmountException' should extend 'Exception'.");
    }

    @DisplayName("Check if 'InsufficientAmountException' class has an 'amount' field")
    @Test
    void testFieldAmountExists() {
        Class<?> clazz = getExceptionClass();
        assertNotNull(clazz, "Class should not be null.");
        try {
            Field field = clazz.getDeclaredField("amount");
            assertNotNull(field, "'amount' field should be present.");
        } catch (NoSuchFieldException e) {
            fail("'amount' field not found in 'InsufficientAmountException'.");
        }
    }

    @DisplayName("Check if 'amount' field is of type double")
    @Test
    void testFieldAmountType() {
        Class<?> clazz = getExceptionClass();
        assertNotNull(clazz, "Class should not be null.");
        try {
            Field field = clazz.getDeclaredField("amount");
            assertEquals(double.class, field.getType(),
                    "'amount' field should be of type 'double'.");
        } catch (NoSuchFieldException e) {
            fail("'amount' field not found in 'InsufficientAmountException'.");
        }
    }

    @DisplayName("Check if 'InsufficientAmountException' class has a constructor with 'double' parameter")
    @Test
    void testConstructorWithDoubleParameter() {
        Class<?> clazz = getExceptionClass();
        assertNotNull(clazz, "Class should not be null.");
        boolean hasConstructor = Arrays.stream(clazz.getConstructors())
                .anyMatch(constructor -> Arrays.equals(constructor.getParameterTypes(), new Class[]{double.class}));
        assertTrue(hasConstructor,
                "'InsufficientAmountException' should have a constructor with 'double' parameter.");
    }

    @DisplayName("Check if 'InsufficientAmountException' class has the required methods")
    @ParameterizedTest(name = "Method {0} should exist with parameters {1}")
    @MethodSource("provideMethods")
    void testMethodExists(String methodName, Class<?>[] paramTypes) {
        Class<?> clazz = getExceptionClass();
        assertNotNull(clazz, "Class should not be null.");
        boolean hasMethod = Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.getName().equals(methodName) &&
                        Arrays.equals(method.getParameterTypes(), paramTypes));
        assertTrue(hasMethod,
                String.format("Method '%s' with parameters %s not found in 'InsufficientAmountException'.",
                        methodName, Arrays.toString(paramTypes)));
    }

    private static Stream<Arguments> provideMethods() {
        return Stream.of(
                Arguments.of("getMessage", new Class[]{}),
                Arguments.of("getAmount", new Class[]{})
        );
    }

//	@DisplayName("Verify that 'getMessage' method returns the correct message")
//	@Test
//	void testGetMessage() {
//		CheckingAccount account = new CheckingAccount(101);
//		account.deposit(370.00);
//		try {
//			account.withdraw(90.00);
//			account.withdraw(300.00);
//		} catch (InsufficientAmountException e) {
//			assertEquals("Sorry, but you are short $20.0", e.getMessage(),
//					"Exception message does not match the expected output.");
//		}
//	}
}
