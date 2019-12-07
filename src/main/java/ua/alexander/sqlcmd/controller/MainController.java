package ua.alexander.sqlcmd.controller;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.JDBCDataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

import java.util.Arrays;

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
        while (true) {
            String input = view.read();
            if (input.equals("help")) {
                help();
            } else if (input.equals("list")) {
                printList();
            } else if (input.equals("exit")) {
                view.type("See ya!");
                System.exit(0);
            } else if (input.startsWith("find:")) {
                find(input);
            }
            else{
                view.type("Sorry, such method doesnt exist!");
            }

        }
    }

    private void find(String input) {
        String [] data = input.split("[:]");
        String tableName = data[1];

        String [] tableColumns = dbManager.getTableColumnNames(tableName);
        drawHeader(tableColumns);

        Data[] tableData = dbManager.getTableData(tableName);
        drawTable(tableData);

    }

    private void drawTable(Data[] tableData) {
        for(Data row : tableData){
            printRow(row);
        }
    }

    private void printRow(Data row) {
        String result = "|";
        for(Object value : row.getValues()){
            result += value + "|";
        }
        view.type(result);
    }

    private void drawHeader(String[] tableColumns) {
        String result = "|";
        for(String name : tableColumns){
            result += name + "|";
        }
        view.type("-----------------------");
        view.type(result);
        view.type("-----------------------");
    }

    private void printList() {
        String[] list = dbManager.getTableNames();
        System.out.println(Arrays.toString(list));
    }

    private void help() {
        view.type("There are such commands:");
        view.type("\tlist -  to get all table names of the database you are connected to.");
        view.type("\thelp - to see all commands available.");
        view.type("\texit - to shut down the program.");
        view.type("\tfind:'your table name' - to draw the table");

    }

    public void connectDB() {
        view.type("Hi, friend! Please insert database name, username and password. Format: database,username,password");
        while (true) {
            try {
                String input = view.read();
                String[] data = input.split("[,]");
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
