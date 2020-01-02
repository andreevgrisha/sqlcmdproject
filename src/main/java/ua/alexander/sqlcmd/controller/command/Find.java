package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Find implements Command {
    private static final String COMMAND_SAMPLE = "find:user";
    private DataBaseManager dbManager;
    private View view;

    public Find(View view, DataBaseManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("find:");
    }

    @Override
    public void execute(String command) {
        try {
            String[] data = command.split("[:]");

            if (data.length != getParameterLength()) {
                throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                        " ,but you need " + getParameterLength());
            }
            String tableName = data[1];

            String[] tableColumns = dbManager.getTableColumnNames(tableName);

            if (tableColumns.length != 0) {
                drawHeader(tableColumns);
                Data[] tableData = dbManager.getTableData(tableName);
                drawTable(tableData);
            }
        } catch (Exception ex) {
            dbManager.printError(ex);
        }
    }

    private int getParameterLength() {
        return COMMAND_SAMPLE.split("[:]").length;
    }


    private void drawTable(Data[] tableData) {
        for (Data row : tableData) {
            printRow(row);
        }
        view.type("|-----------------------|");
    }

    private void printRow(Data row) {
        String result = "|";
        for (Object value : row.getValues()) {
            result += value + "|";
        }
        view.type(result);
    }

    private void drawHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.type("|-----------------------|");
        view.type(result);
        view.type("|-----------------------|");
    }
}
