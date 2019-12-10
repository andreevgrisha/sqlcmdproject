package ua.alexander.sqlcmd.controller;

import ua.alexander.sqlcmd.controller.command.*;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.JDBCDataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;


public class MainController {
    private View view;
    private Command[] commands;
    private DataBaseManager dbManager;

    public MainController(View view, DataBaseManager dbManager) {
        this.view = new Console();
        this.dbManager = new JDBCDataBaseManager();
        this.commands = new Command[]{
                new Connect(view,dbManager),
                new Exit(view),
                new Help(view),
                new IsConnected(view,dbManager),
                new List(view, dbManager),
                new Find(view, dbManager),
                new Unsupported(view)
        };
    }

    public void run() {
        view.type("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password");
        while(true){
            String input = view.read();
            for(Command command : commands) {
                if (command.processAble(input)) {
                    command.execute(input);
                    break;
                }
            }

        }
    }
}
