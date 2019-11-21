package ua.alexander.sqlcmd.module;

import java.util.Arrays;

public class Data {
    static class MetaData {
        private String name;
        private Object value;

        public MetaData(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    public MetaData[] data = new MetaData[100]; // TODO remove magic number 100
    public int index = 0;

    public void add(String name, Object value){
        data[index++] = new MetaData(name, value);
    }


    public Object[] getValues() {
        Object[] result = new Object[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return
                "names:" + Arrays.toString(getNames()) + "\n" +
                "values:" + Arrays.toString(getValues());

    }

}
