package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.view.View;

public class Help implements Command{

    private View view;
    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.equals("help");
    }

    @Override
    public void execute(String command) {
        view.type("There are such commands:");
        view.type("\thelp - to see all commands available.");
        view.type("\tconnect:database,username,password  - to connect to a certain database");
        view.type("\tlist -  to get all table names of the database you are connected to.");
        view.type("\tinsert:tableName|column1|value1|column2|value2|...|columnN|valueN - to make a new record in the table");
        view.type("\tfind:tableName - to draw the table");
        view.type("\tclear:tableName - to clear table's content");
        view.type("\texit - to shut down the program.");

    }
}
