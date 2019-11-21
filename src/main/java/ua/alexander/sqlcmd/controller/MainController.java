package ua.alexander.sqlcmd.controller;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.JDBCDataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

public class MainController {
    public static void main(String[] args) {
        View view = new Console();
        DataBaseManager dbManager = new JDBCDataBaseManager();
        view.type("Hi, friend! Please insert database name, username and password. Format: database,username,password");

        while (true) {
        String input = view.read();
        String[] data = input.split("[,]");
        String database = data[0];
        String username = data[1];
        String password = data[2];
        try {
            dbManager.connect(database, username, password);
            view.type("\u001B[34m" + "Success!" + "\u001B[0m");
            break;
        } catch (Exception e) {
            String message = e.getMessage();
            if(e.getCause() != null){
                message += " " + e.getCause().getMessage();
            }
            view.type("\u001B[31m" + "Failed, the reason is: " + message + "\u001B[0m" + "\nTry again!");

        }
    }
    }
}
