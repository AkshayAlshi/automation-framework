package config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try {
            ClassLoader classLoader = Config.class.getClassLoader();
            InputStream input = classLoader.getResourceAsStream("config.properties");
            if (input == null) {
                throw new RuntimeException("⚠️ config.properties not found in classpath");
            }
            properties.load(input);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}