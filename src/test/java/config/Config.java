package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            // ignore - defaults handled below
        }
    }

    private Config() {}

    public static String getBaseUrl() {
        String v = System.getProperty("base.url");
        if (v != null && !v.isEmpty()) return v;
        return props.getProperty("base.url", "https://jsonplaceholder.typicode.com");
    }

    public static int getTimeoutMillis() {
        String v = System.getProperty("timeout");
        if (v != null && !v.isEmpty()) {
            try {
                return Integer.parseInt(v);
            } catch (NumberFormatException ignored) {}
        }
        String p = props.getProperty("timeout", "5000");
        try {
            return Integer.parseInt(p);
        } catch (NumberFormatException e) {
            return 5000;
        }
    }
}
