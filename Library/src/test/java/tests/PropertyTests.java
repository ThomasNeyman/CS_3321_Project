package tests;

import Game.*;
import org.junit.jupiter.api.*;

/*
 There really isn't a lot to test with the property class because
 it simply holds information and doesn't provide a lot of functionality
*/
public class PropertyTests {

    Property p;

    @BeforeEach
    public void setUp() {
        p = new Property(50, 50 ,0, "Test Avenue");
    }

    @AfterEach
    public void tearDown() {
        p = null;
    }

    @Test
    @DisplayName("Test to make sure properties are created properly")
    public void testProperty_a() {
        Assertions.assertEquals(50, p.getCost());
        Assertions.assertEquals(50, p.getRent());
        Assertions.assertEquals(0, p.getPosition());
    }

    @Test
    @DisplayName("Test to make sure properties are created properly")
    public void testProperty_b() {
        p.setPosition(10);
        p.setCost(1000);
        p.setRent(557);

        Assertions.assertEquals(1000, p.getCost());
        Assertions.assertEquals(557, p.getRent());
        Assertions.assertEquals(10, p.getPosition());
    }

    //TODO
    // Make tests to make sure the values of house rents and costs will be integers

}
