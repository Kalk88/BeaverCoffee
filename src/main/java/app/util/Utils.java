package app.util;

import java.util.UUID;

public class Utils {

    /**
     * Returns a random v4 UUID as string.
     * @return String
     */
    public static String getUUIDString() {
        return UUID.randomUUID().toString();
    }
}
