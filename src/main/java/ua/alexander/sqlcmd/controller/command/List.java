package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.Arrays;

public class List implements Command {
    private DataBaseManager dbManager;
    private View view;

    public List(View view,DataBaseManager dbManager){
        this.view = view;
        this.dbManager = dbManager;
    }
    @Override
    public boolean processAble(String command) {
        return command.equals("list");
    }

    @Override
    public void execute(String command) {
        String[] list = dbManager.getTableNames();
        view.type(Arrays.toString(list));

    }
}
