package ua.alexander.sqlcmd.controller;

import ua.alexander.sqlcmd.controller.command.*;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;


public class MainController {
    private View view;
    private Command[] commands;

    public MainController(View view, DataBaseManager dbManager) {
        this.view = new Console();
        this.commands = new Command[]{
                new Connect(view,dbManager),
                new IsConnected(view,dbManager),
                new Exit(view),
                new Help(view),
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
            view.type("Please enter you command! Type 'help' to see available commands.");
        }
    }
}
