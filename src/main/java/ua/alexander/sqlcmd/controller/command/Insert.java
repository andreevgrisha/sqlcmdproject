package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Insert implements Command {
    private final DataBaseManager dbManager;
    private final View view;

    public Insert(View view,DataBaseManager dbManager) {
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("insert:");
    }

    @Override
    public void execute(String command) {
        try {
            String[] input = command.split("[:]");
            if (input.length % 2 != 0) {
                throw new IllegalArgumentException(String.format("Some parameters are missing. " +
                        "The command should look like that: " +
                        "'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                        "but your is: '%s'", command));
            }
            String tableName = input[1];

            Data data = new Data();
            for (int index = 1; index < (input.length / 2); index++) {
                String columnName = input[index * 2];
                String value = input[index * 2 + 1];

                data.put(columnName, value);
            }
            dbManager.insertData(tableName, data);
            view.type(String.format("Record %s was successfully added to the table '%s'.", data, tableName));

        }catch (Exception ex) {
            dbManager.printError(ex);
        }
    }
}
