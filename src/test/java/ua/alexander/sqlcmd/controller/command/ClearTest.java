package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ClearTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Clear(view, dbManager);
    }

    @Test
    public void testProcessAbleClearWithParametersString() {
        boolean processAble = command.processAble("clear:user");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleClearWithoutParametersString() {
        boolean processAble = command.processAble("clear");
        assertFalse(processAble);
    }

    @Test
    public void testClearTable() {
        command.execute("clear:user");
        Mockito.verify(dbManager).clearTable("user");
        Mockito.verify(view).type("Table 'user' was cleared successfully!");
    }

    @Test
    public void testClearTableWrongParameters() {
        try {
            command.execute("clear:");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 1 ,but you need 2", e.getMessage());
        }
        try {
            command.execute("clear:clear:user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }

    }



}
