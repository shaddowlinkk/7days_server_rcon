package Core.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configeration {
    private Properties prop;
    private FileInputStream is;

    public Configeration(String fileName) {
        prop = new Properties();
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {

        }
        try {
            prop.load(is);
        } catch (IOException e) {

        }
    }

    public String getProp(String propName) {
        return prop.getProperty(propName);
    }
}
