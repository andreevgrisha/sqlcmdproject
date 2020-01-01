package ua.alexander.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import ua.alexander.sqlcmd.controller.Main;

import java.io.PrintStream;

public class IntegrationTest {

    private  ConfigurableInputStream in;
    private  LogOutputStream out;

    @Before
    public void setup(){
        in = new ConfigurableInputStream();
        out = new LogOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit(){
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                //exit
                "See ya!\r\n",out.getData());
    }

    @Test
    public void testHelp(){
        in.add("help");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                //help
                "There are such commands:\r\n" +
                "\thelp - to see all commands available.\r\n" +
                "\tconnect:database,username,password  - to connect to a certain database\r\n" +
                "\tlist -  to get all table names of the database you are connected to.\r\n" +
                "\tfind: database - to draw the table\r\n" +
                "\texit - to shut down the program.\r\n" +
                //exit
                "See ya!\r\n",out.getData());
    }

    @Test
    public void testCommandWithOutConnection(){
        in.add("list");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                //list
                "Sorry, but you can't use command 'list' before you connect the database. To connect database type 'connect:database,username,password'\r\n"+
                //exit
                "See ya!\r\n",out.getData());
    }

    @Test
    public void testFind(){
        in.add("connect:sqlcmd,postgres,1234");
        in.add("find:user");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                //connect
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //find
                "|-----------------------|\r\n"+
                "|id|username|password|\r\n" +
                "|-----------------------|\r\n"+
                "|8|Andreev|qwerty1234|\r\n" +
                //exit
                "See ya!\r\n",out.getData());
    }

    @Test
    public void testList(){
        in.add("connect:sqlcmd,postgres,1234");
        in.add("list");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                //connect
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //list
                "[user, test]\r\n" +
                //exit
                "See ya!\r\n",out.getData());
    }

    @Test
    public void testUnsupported(){
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                //connect
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //exit
                "See ya!\r\n",out.getData());
    }

}
