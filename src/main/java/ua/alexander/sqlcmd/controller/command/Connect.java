package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;


public class Connect implements Command {
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
            String[] data = command.split("[,]");
            data[0] = data[0].substring(8);
            if (data.length != 3) {
                throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length + " ,but you need 3");
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

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.type("\u001B[31m" + "Failed, the reason is: " + message + "\u001B[0m" + "\nTry again!");
    }
}
