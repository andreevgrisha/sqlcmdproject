package ua.alexander.sqlcmd.module;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseManager dbManager = new JDBCDataBaseManager();
        dbManager.connect("sqlcmd","postgres","1234");
    }
}























