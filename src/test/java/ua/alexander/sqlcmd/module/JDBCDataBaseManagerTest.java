package ua.alexander.sqlcmd.module;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

public class JDBCDataBaseManagerTest {
    private DataBaseManager jdbcDBManager;
    @Before
    public void setup() {
        jdbcDBManager = new JDBCDataBaseManager();
        jdbcDBManager.connect("sqlcmd", "postgres", "1234");
    }

    @Test
    public void testGetTableNames() {
        String[] tableNames = jdbcDBManager.getTableNames();
        System.out.println(Arrays.toString(tableNames));
        assertEquals("[user, test]", Arrays.toString(tableNames));

    }

    @Test
    public void testGetTableData() {
        jdbcDBManager.clearTable("user");

        Data input = new Data();
        input.add("id", 8);
        input.add("username", "Andreev");
        input.add("password", "qwerty");
        jdbcDBManager.insertData("user", input);

        Data[] users = jdbcDBManager.getTableData("user");
        Data user = users[0];

        assertEquals("[id, username, password]", Arrays.toString(user.getNames()));
        assertEquals("[8, Andreev, qwerty]", Arrays.toString(user.getValues()));

        System.out.println("#Table Data:#\n\n" + Arrays.toString(jdbcDBManager.getTableData("user")));
    }

    @Test
    public void testUpdateTableData() {
        jdbcDBManager.clearTable("user");

        Data input = new Data();
        input.add("id", 8);
        input.add("username", "Andreev");
        input.add("password", "qwerty");
        jdbcDBManager.insertData("user",input);

        Data update = new Data();
        update.add("password", "qwerty1234");
        jdbcDBManager.updateTableData("user", update ,8);

        Data[] users = jdbcDBManager.getTableData("user");
        Data user = users[0];

        assertEquals("[id, username, password]", Arrays.toString(user.getNames()));
        assertEquals("[8, Andreev, qwerty1234]", Arrays.toString(user.getValues()));

        System.out.println("#Updated table#\n\n" + Arrays.toString(jdbcDBManager.getTableData("user")));
    }


}