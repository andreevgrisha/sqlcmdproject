package ua.alexander.sqlcmd.controller.command;

public interface Command {
    boolean processAble(String command);
    void execute(String command);
}
