package API_utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static final String PROPERTIES_FILE_PATH = "config.properties";
    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Properties file not found, creating a new one.");
        }
    }
    
    
     // Method to get a property by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Method to set a property and save it to the file
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.store(output, "Updated " + key);
            System.out.println("Property " + key + " has been updated.");
        } catch (IOException e) {
            System.err.println("Error saving property to file: " + e.getMessage());
        }
    }
    
}
