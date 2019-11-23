package ua.alexander.sqlcmd.controller;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.JDBCDataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

public class MainController {

    private View view;
    private DataBaseManager dbManager;

    public MainController(View view, DataBaseManager dbManager) {
        this.view = new Console();
        this.dbManager = new JDBCDataBaseManager();
    }

    public void run() {
        connectDB();
        view.type("Please enter you command! Type 'help' to see available commands.");
        String input = view.read();
        switch (input){
            case "help": help();
            case "list": getList();
        }
    }

    private void getList() {
       String [] list = dbManager.getTableNames();
    }

    private void help() {
        
    }

    public void connectDB() {
        view.type("Hi, friend! Please insert database name, username and password. Format: database,username,password");
        while (true) {
            try {
                String input = view.read();
                String[] data = input.split("[/]");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length + " ,but you need 3");
                }
                String database = data[0];
                String username = data[1];
                String password = data[2];
                dbManager.connect(database, username, password);
                break;
            } catch (Exception e) {
                printError(e);
            }
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
