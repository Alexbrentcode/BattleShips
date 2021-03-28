import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard testGame = new GameBoard();
    InputStream systemIn = System.in;
    ByteArrayInputStream testIn;
    ByteArrayOutputStream testOut;

    @Before
    void setOutput(){
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    void setInput(String data){
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    String getOutput(){
        return testOut.toString();
    }

    /*
    * Very unsure of how to test user Input through JUnit when using System.im - research and testing needed.
    * */

    @org.junit.jupiter.api.Test
    void testInputEquality() {
        final String testString = "A5";
        setInput(testString);
        assertEquals(testGame.parseInput(), getOutput());
    }


    @Disabled
    @org.junit.jupiter.api.Test
    void validShot() {
            testGame.shipCoordinates[0][0] = "05";
            testGame.shipCoordinates[0][1] = "15";
            testGame.shipCoordinates[0][2] = "25";
            assertTrue(testGame.playerShot("A5"));
            assertTrue(testGame.playerShot("B5"));
            assertTrue(testGame.playerShot("C5"));
        }
    @Disabled
    @org.junit.jupiter.api.Test
    void invalidShot() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            testGame.playerShot("A0");
        });
    }

}