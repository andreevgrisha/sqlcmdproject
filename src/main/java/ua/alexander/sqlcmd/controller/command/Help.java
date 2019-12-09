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
        view.type("\tlist -  to get all table names of the database you are connected to.");
        view.type("\thelp - to see all commands available.");
        view.type("\texit - to shut down the program.");
        view.type("\tfind:'your table name' - to draw the table");

    }
}
