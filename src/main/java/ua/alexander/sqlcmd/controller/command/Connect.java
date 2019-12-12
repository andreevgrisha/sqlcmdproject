package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;


public class Connect implements Command {
    private static final  String COMMAND_SAMPLE = "connect:sqlcmd,postgres,1234";

    private View view;
    private DataBaseManager dbManager;

    public Connect(View view, DataBaseManager dbManager) {
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("connect:");
    }

    @Override
    public void execute(String command) {
        try {
            String [] data = getCommandRefactored(command);

            if (data.length != getParameterLength()) {
                throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                        " ,but you need " + getParameterLength());
            }
            String database = data[0];
            String username = data[1];
            String password = data[2];
            dbManager.connect(database, username, password);
            view.type("\u001B[34m" + "Success!" + "\u001B[0m");
            view.type("Please enter you command! Type 'help' to see available commands.");
        } catch (Exception e) {
            printError(e);
        }

    }

    private int getParameterLength() {
        return COMMAND_SAMPLE.split("[,]").length;
    }

    private String[] getCommandRefactored(String command){
        String [] refactored = command.split("[,]");
        String [] buffer = refactored[0].split("[:]");
        if(buffer.length == 2){
            refactored[0] = buffer[1];
        }else{
            return new String[0];
        }
        return refactored;
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.type("\u001B[31m" + "Failed, the reason is: " + message + "\u001B[0m" + "\nTry again!");
    }
}
