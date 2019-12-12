package ua.alexander.sqlcmd.module;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

public class JDBCDataBaseManagerTest extends DataBaseManagerTest{
    @Override
    public DataBaseManager getDataBaseManager() {
        return new JDBCDataBaseManager();
    }
}
