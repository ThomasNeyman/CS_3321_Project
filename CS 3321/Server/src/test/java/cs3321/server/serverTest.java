package cs3321.server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class serverTest {

    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server();
    }

    @Test
    void Test_HelloWorld() {
        assertTrue(true);
    }
}
