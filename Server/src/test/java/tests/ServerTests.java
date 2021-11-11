/*
using https://junit.org/junit5/docs/current/user-guide/#writing-tests for test guides
https://javalin.io/tutorials/testing for javalin testing
 */

package tests;

import cs3321.*;
import org.junit.jupiter.api.*;
//import static org.assertj.core.api.Assertions.*;

public class ServerTests {

    //region cs3321.Server tests

    private static final MoServer MO_SERVER = new MoServer();

    @BeforeAll
    //private static void setUp() {
        // start the server
        // a start method needs to be implemented so you are not
        // calling the main function
        //MO_SERVER.startServer();
   // }

    @AfterAll
   // private static void tearDown() {
        //stop the server
   //     MO_SERVER.getApp().stop();
   // }

    @Test
    @DisplayName("Assert the server has been created")
    //@Disabled("Not Implemented")
    void showAssertion() {
        Assertions.assertNotNull(MO_SERVER);
    }

    //endregion
}
