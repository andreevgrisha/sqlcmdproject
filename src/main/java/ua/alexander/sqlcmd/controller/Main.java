package ua.alexander.sqlcmd.controller;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.JDBCDataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DataBaseManager dbManager = new JDBCDataBaseManager();
        MainController mc = new MainController(view, dbManager);
        mc.run();

    }
}

