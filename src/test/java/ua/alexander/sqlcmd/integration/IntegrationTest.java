package ua.alexander.sqlcmd.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import ua.alexander.sqlcmd.controller.Main;

import java.io.PrintStream;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static LogOutputStream out;

    @BeforeClass
    public static void setup(){
        in = new ConfigurableInputStream();
        out = new LogOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit(){
        in.add("help");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("",out.getData());
    }

}
