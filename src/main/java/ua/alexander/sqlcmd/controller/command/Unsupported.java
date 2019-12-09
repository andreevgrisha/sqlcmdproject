package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.view.View;

public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return true;
    }

    @Override
    public void execute(String command) {
        view.type("Sorry, such command doesn't exist! Try again!");
    }
}
