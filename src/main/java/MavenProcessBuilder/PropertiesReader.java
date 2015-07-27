package MavenProcessBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final String PROP_FILE = "ApplicationProperties.properties";

    public String getPropertiesByName(String name) {
        Properties properties = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Sorry, can't find file " + PROP_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }
}
