package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class IsConnected implements Command {
    private View view;
    private DataBaseManager dbManager;
    public IsConnected(View view, DataBaseManager dbManager) {
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return !dbManager.isConnected();
    }

    @Override
    public void execute(String command) {
        view.type(String.format("Sorry, but you can't use command '%s' before you connect the database. " +
                "To connect database type: connect:database,username,password",command));
    }
}
