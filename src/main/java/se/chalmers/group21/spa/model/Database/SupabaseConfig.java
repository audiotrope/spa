package se.chalmers.group21.spa.model.Database;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SupabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream input =
                     new FileInputStream("supabase.properties")) {

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not load Supabase configuration", e
            );
        }
    }

    public static String getUrl() {
        return properties.getProperty("supabase.url");
    }

    public static String getKey() {
        return properties.getProperty("supabase.key");
    }
}
