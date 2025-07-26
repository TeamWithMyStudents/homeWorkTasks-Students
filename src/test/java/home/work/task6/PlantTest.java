package home.work.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    private static final String PLANT_CLASS_NAME = "home.work.task6.Plant";

    @DisplayName("Check if 'tryCreatePlant' method exists")
    @Test
    void testTryCreatePlantMethodExists() {
        try {
            Class<?> clazz = Class.forName(PLANT_CLASS_NAME);
            Method method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> "tryCreatePlant".equals(m.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("'tryCreatePlant' method not found."));

            assertArrayEquals(new Class[]{String.class, String.class, String.class},
                    method.getParameterTypes(),
                    "'tryCreatePlant' method must have parameters (String, String, String).");

        } catch (ClassNotFoundException e) {
            fail("Class 'Plant' not found.");
        }
    }

    @DisplayName("Check if 'tryCreatePlant' method returns a Plant object")
    @Test
    void testTryCreatePlantMethodReturnType() {
        try {
            Class<?> clazz = Class.forName(PLANT_CLASS_NAME);
            Method method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> "tryCreatePlant".equals(m.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("'tryCreatePlant' method not found."));

            assertEquals(Plant.class, method.getReturnType(),
                    "'tryCreatePlant' method should return an object of type 'Plant'.");

        } catch (ClassNotFoundException e) {
            fail("Class 'Plant' not found.");
        }
    }

    @DisplayName("Check if 'tryCreatePlant' method is static")
    @Test
    void testTryCreatePlantMethodIsStatic() {
        try {
            Class<?> clazz = Class.forName(PLANT_CLASS_NAME);
            Method method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> "tryCreatePlant".equals(m.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("'tryCreatePlant' method not found."));

            assertTrue(Modifier.isStatic(method.getModifiers()),
                    "'tryCreatePlant' method should be static.");

        } catch (ClassNotFoundException e) {
            fail("Class 'Plant' not found.");
        }
    }

//    @DisplayName("Check if 'tryCreatePlant' method creates a valid Plant object")
//    @ParameterizedTest(name = "Creating plant with type={0}, color={1}, name={2}")
//    @MethodSource("provideValidPlantData")
//    void testTryCreatePlantCreatesValidPlant(String type, String color, String name) throws ColorException, TypeException {
//        Plant plant = Plant.tryCreatePlant(type, color, name);
//        assertNotNull(plant,
//                String.format("Plant creation failed for type='%s', color='%s', name='%s'.",
//                        type, color, name));
//    }

    private static Stream<Arguments> provideValidPlantData() {
        return Stream.of(
                Arguments.of("Rare", "MyColor", "MyName"),
                Arguments.of("MyType", "Red", "MyName"),
                Arguments.of("MyType", "MyColor", "MyName")
        );
    }

}
