package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Find implements Command {
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
        String [] data = command.split("[:]");
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
        view.type("|-----------------------|");
        view.type(result);
        view.type("|-----------------------|");
    }
}
