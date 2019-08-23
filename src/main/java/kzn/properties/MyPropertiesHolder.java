package kzn.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MyPropertiesHolder {
    @SuppressWarnings("WeakerAccess")
    public static int MODE_CREATE = 0;
    public static int MODE_UPDATE = 1;
    private String filename;
    private Properties properties;

    public MyPropertiesHolder(String filename, int mode) throws IOException {
        this.filename = filename;
        this.properties = new Properties();
        if (mode != MODE_CREATE) {
            FileInputStream inputStream = new FileInputStream(filename);
            properties.load(inputStream);
            inputStream.close();
        }
    }

    // возврат свойства по ключу
    public String getProperty(String key) {
        return (String) properties.get(key);
    }

    // добавление или перезапись свойства
    @SuppressWarnings("unused")
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    // запись в хранилище свойств
    public void commit() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filename);
        properties.store(outputStream, "");
        outputStream.close();
    }

    // список свойств
    public Properties getProperties() {
        return properties;
    }


}
