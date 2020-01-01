package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.view.View;

public class Exit implements Command {

    private View view;
    public Exit(View view){
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.equals("exit");
    }

    @Override
    public void execute(String command) {
        view.type("See ya!");
        throw new ExitException();
    }
}
