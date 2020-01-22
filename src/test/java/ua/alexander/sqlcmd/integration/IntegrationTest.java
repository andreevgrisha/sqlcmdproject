package ua.alexander.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import ua.alexander.sqlcmd.controller.Main;

import java.io.PrintStream;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private LogOutputStream out;

    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new LogOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit() {
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testHelp() {
        in.add("help");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //help
                "There are such commands:\r\n" +
                "\thelp - to see all commands available.\r\n" +
                "\tconnect:database,username,password  - to connect to a certain database\r\n" +
                "\tlist -  to get all table names of the database you are connected to.\r\n" +
                "\tinsert:tableName|column1|value1|column2|value2|...|columnN|valueN - to make a new record in the table\r\n" +
                "\tfind:tableName - to draw the table\r\n" +
                "\tclear:tableName - to clear table's content\r\n" +
                "\texit - to shut down the program.\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testConnectAfterConnect() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //connect
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testConnectError() {
        in.add("connect:error");
        in.add("connect:");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error 1
                "[31mFailed, the reason is: Something is missing... Quantity of parameters is 1 ,but you need 3[0m\n" +
                "Try again!\r\n" +
                //error 2
                "[31mFailed, the reason is: Something is missing... Quantity of parameters is 0 ,but you need 3[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testCommandWithOutConnection() {
        in.add("list");
        in.add("find");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //list
                "Sorry, but you can't use command 'list' before you connect the database. " +
                "To connect database type 'connect:database,username,password'\r\n" +
                //find
                "Sorry, but you can't use command 'find' before you connect the database." +
                " To connect database type 'connect:database,username,password'\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testFind() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("insert:user,id,20,username,d,password,1234");
        in.add("insert:user,id,8,username,s,password,4321");
        in.add("find:user");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //clear
                "Table 'user' was cleared successfully!\r\n" +
                //find
                "Record names:[id, username, password]\n" +
                "values:[20, d, 1234] was successfully added to the table 'user'.\r\n" +
                "Record names:[id, username, password]\n" +
                "values:[8, s, 4321] was successfully added to the table 'user'.\r\n" +
                "|-----------------------|\r\n" +
                "|id|username|password|\r\n" +
                "|-----------------------|\r\n" +
                "|20|d|1234|\r\n" +
                "|8|s|4321|\r\n" +
                "|-----------------------|\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testFindError() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("insert:user,id,20,username,d,password,1234");
        in.add("find:");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //clear
                "Table 'user' was cleared successfully!\r\n" +
                //find
                "Record names:[id, username, password]\n" +
                "values:[20, d, 1234] was successfully added to the table 'user'.\r\n" +
                "\u001B[31mFailed, the reason is: Something is missing... Quantity of parameters is 1 ,but you need 2\u001B[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testInsertError() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("insert:user,id,20,username,d,password");
        in.add("insert:");

        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //clear
                "Table 'user' was cleared successfully!\r\n" +
                //insert
                "\u001B[31mFailed, the reason is: Some parameters are missing. The command should look like that: \n" +
                "'insert:tableName,column1,value1,column2,value2...columnN,valueN', but your is: 'insert:user,id,20,username,d,password'\u001B[0m\n" +
                "Try again!\r\n" +
                "\u001B[31mFailed, the reason is: Some parameters are missing. The command should look like that: \n" +
                "'insert:tableName,column1,value1,column2,value2...columnN,valueN', but your is: 'insert:'\u001B[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }


    @Test
    public void testList() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("list");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //list
                "[user, test]\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }


    @Test
    public void testUnsupported() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testClear() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                "Table 'user' was cleared successfully!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testClearError() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:");
        in.add("clear:gf");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //error 1
                "[31mFailed, the reason is: Something is missing... Quantity of parameters is 1 ,but you need 2[0m\n" +
                "Try again!\r\n" +
                //error 2
                "\u001B[31mFailed, the reason is: ERROR: relation \"public.gf\" does not exist\n" +
                "  Position: 13\u001B[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }


}
