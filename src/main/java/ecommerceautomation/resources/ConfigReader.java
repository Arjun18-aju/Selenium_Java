package ecommerceautomation.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE = "GlobalData.properties";
    private static final Properties properties = loadProperties();

    private ConfigReader() {
        // utility class
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IOException("Unable to find " + CONFIG_FILE + " on classpath");
            }
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + CONFIG_FILE, e);
        }
        return props;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getUrl() {
        return getProperty("url");
    }

    public static String getUsername() {
        return getProperty("username");
    }

    public static String getPassword() {
        return getProperty("password");
    }
}
