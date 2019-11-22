package ua.alexander.sqlcmd.module;

import ua.alexander.sqlcmd.controller.MainController;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        View view = new Console();
        DataBaseManager dbManager = new JDBCDataBaseManager();
        MainController mc = new MainController(view, dbManager);

        mc.connectDB();
    }
}























