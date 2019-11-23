package ua.alexander.sqlcmd.module;

import java.sql.*;
import java.util.Arrays;

public class JDBCDataBaseManager implements DataBaseManager {
    private Connection connection;


    public void connect(String database, String user, String password)  {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Please add you PosgreSQL Driver to the project!",e);
            }
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database ,user ,password);
            System.out.println("\u001B[34m" + "Success!" + "\u001B[0m");
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("Can't get connection to database '%s', with the user: '%s'", database, user),e);
        }
    }


    public String [] getTableNames(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT table_name FROM information_schema.tables " +
                    "WHERE table_type = 'BASE TABLE' AND table_schema = 'public'"));
            String[] tables = new String [10];
            int index = 0;
            while(resultSet.next()){
                tables[index++] = resultSet.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            resultSet.close();
            statement.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }



    public Data[] getTableData(String tableName) {
        try {
            Statement statement = connection.createStatement();
            int size = getColumnCount(tableName);

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM public.%s", tableName));
            ResultSetMetaData resultSetMD = resultSet.getMetaData();
            Data[] output = new Data[size];

            int index = 0;
            while(resultSet.next()){
                Data data = new Data();
                output[index++] = data;
                for(int i = 1; i <= resultSetMD.getColumnCount(); i++) {
                    data.add(resultSetMD.getColumnName(i), resultSet.getObject(i));
                }
            }
            statement.close();
            resultSet.close();
            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void clearTable(String tableName){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("DELETE FROM public.%s", tableName));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertData(String tableName, Data input) {
        try {
            Statement statement = connection.createStatement();

            String tableNames = formatNames(input, "%s,");
            String values = formatValues(input,"'%s',");

            statement.executeUpdate(String.format("INSERT INTO public."+ tableName +"(%s)VALUES (%s)", tableNames, values));
            statement.close();
        }
         catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateTableData(String tableName, Data updatedValue, int id){
        String updatedNames = formatNames(updatedValue, "%s = ?,");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public." + tableName
                    + " SET " + updatedNames + " WHERE id = ?");

            int index = 1;
            for(Object value : updatedValue.getValues()) {
                preparedStatement.setString(index, value.toString());
                index++;
            }
            preparedStatement.setInt(index, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getColumnCount(String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSetCount = statement.executeQuery(String.format("SELECT COUNT (*) FROM public.%s", tableName));
        resultSetCount.next();
        int size = resultSetCount.getInt(1);
        resultSetCount.close();
        statement.close();
        return size;
    }

    public String formatNames(Data input, String format) {
        String names = "";
        for (String name : input.getNames()) {
            names += String.format(format,name);
        }
        names = names.substring(0, names.length() - 1);
        return names;
    }

    public String formatValues(Data input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format,value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

}

