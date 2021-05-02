package animals.GameInformation;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {

    private final ResourceBundle messages = ResourceBundle.getBundle("messages");
    private final ResourceBundle patterns = ResourceBundle.getBundle("patterns");

    public ResourceBundle getMessages() {
        return messages;
    }

    public ResourceBundle getPatterns() {
        return patterns;
    }

}
