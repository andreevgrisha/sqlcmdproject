package ua.alexander.sqlcmd.module;

public interface DataBaseManager {
    void connect(String database, String user, String password);

    String [] getTableNames();

    Data[] getTableData(String tableName);

    void clearTable(String tableName);

    void insertData(String tableName,Data input);

    void updateTableData(String tableName, Data updatedValue, int id);
}
