/*
using https://junit.org/junit5/docs/current/user-guide/#writing-tests for test guides
https://javalin.io/tutorials/testing for javalin testing
 */

import org.junit.jupiter.api.*;
//import static org.assertj.core.api.Assertions.*;

public class UnitTests {

    //region Server tests

    private static final Server server = new Server();

    @BeforeAll
    private static void setUp() {
        // start the server
        // a start method needs to be implemented so you are not
        // calling the main function
        server.startServer();
    }

    @AfterAll
    private static void tearDown() {
        //stop the server
        server.getApp().stop();
    }

    @Test
    @DisplayName("Assert the server has been created")
    //@Disabled("Not Implemented")
    void showAssertion() {
        Assertions.assertNotNull(server);
    }

    //endregion

    //region player tests

    //endregion

    //region Monopoly tests

    //endregion

    //region card tests

    //endregion

    //region chance tests

    //endregion

    //region community chest tests

    //endregion

    //region Tax tests

    //endregion

    //region square tests

    //endregion

}
