package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Clear implements Command {
    private static final String COMMAND_SAMPLE = "clear:user";
    private DataBaseManager dbManager;
    private View view;

    public Clear(View view, DataBaseManager dbManager) { //TODO проверка при вызове комманды
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("clear:");
    }

    @Override
    public void execute(String command) {
        String[] data = command.split("[:]");

        if (data.length  != getParameterLength()) {
            throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                    " ,but you need " + getParameterLength());
        }
        String tableName = data[1];
        dbManager.clearTable(tableName);
        view.type(String.format("Table '%s' was cleared successfully!", tableName));
    }

    private int getParameterLength() {
        return COMMAND_SAMPLE.split("[:]").length ;
    }
}
