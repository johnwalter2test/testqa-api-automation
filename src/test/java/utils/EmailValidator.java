package utils;

import java.util.regex.Pattern;

public class EmailValidator {

    // Require local part, @, domain with at least one dot and TLD of 2+ letters
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";

    public static boolean isValid(String email) {
        if (email == null) return false;
        email = email.trim();
        if (email.isEmpty()) return false;
        return Pattern.matches(EMAIL_REGEX, email);
    }
}