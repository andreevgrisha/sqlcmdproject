package ua.alexander.sqlcmd.module;

import ua.alexander.sqlcmd.controller.MainController;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseManager dbManager = new JDBCDataBaseManager();
        dbManager.connect("sqlcmd","postgres","1234");
    }
}























