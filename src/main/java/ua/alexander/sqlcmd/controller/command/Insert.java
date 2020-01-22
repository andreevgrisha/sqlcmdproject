package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Insert implements Command {
    private final DataBaseManager dbManager;
    private final View view;

    public Insert(View view, DataBaseManager dbManager) {
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("insert:");
    }

    @Override
    public void execute(String command) {
        String[] input = getCommandRefactored(command);
        if (input.length % 2 != 1) {
            throw new IllegalArgumentException(String.format("Some parameters are missing. " +
                    "The command should look like that: \n" +
                    "'insert:tableName,column1,value1,column2,value2...columnN,valueN', " +
                    "but your is: '%s'", command));
        }
        String tableName = input[0];

        Data data = new Data();
        for (int index = 1; index < input.length; index = index + 2) {
            String columnName = input[index];
            String value = input[index + 1];

            data.put(columnName, value);
        }
        dbManager.insertData(tableName, data);
        view.type(String.format("Record %s was successfully added to the table '%s'.", data, tableName));
    }

    private String[] getCommandRefactored(String command) {
        String[] refactored = command.split("[,]");
        String[] buffer = refactored[0].split("[:]");
        if (buffer.length == 2) {
            refactored[0] = buffer[1];
        } else {
            return new String[0];
        }
        return refactored;
    }
}
