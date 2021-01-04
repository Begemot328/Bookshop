package by.epam.bookshop.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private final static   String REGEX
            = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static boolean validateURL(String url) {
        Pattern pattern = Pattern.compile(REGEX);
        return pattern.matcher(url).matches();
    }
}
