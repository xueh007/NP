package ime.contrib.np.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil {
    public static String MESSAGE_FILE = "ime.contrib.np.i18n.message";

    public static String getMessage(String key) {
        return getMessage(key, Locale.getDefault(), MESSAGE_FILE);
    }

    public static String getMessage(String key, Locale locale, String messageFile) {
        ResourceBundle bundle = ResourceBundle.getBundle(messageFile, locale);

        if(bundle.containsKey(key)) {
            return bundle.getString(key);
        }

        return "!" + key + "!";
    }
}
