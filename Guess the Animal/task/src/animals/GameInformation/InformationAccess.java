package animals.GameInformation;

import animals.GameComponents.Engine.Interogator;
import animals.GameComponents.Engine.Interpreter;

import java.util.Random;

public class InformationAccess {
    private static final Localizer localizer = new Localizer();
    private static final GameInformation gameInformation = new GameInformation();
    private static final Interpreter interpreter = new Interpreter();
    private static final Interogator interogator = new Interogator();

    public static GameInformation getGameInformation() {
        return gameInformation;
    }

    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static Interogator getInterogator() {
        return interogator;
    }

    public static Localizer getLocalizer() {
        return localizer;
    }

    public static String getMessagesKey(String key) {
        return localizer.getMessages().getString(key);
    }

    public static String getRandomMessagesKey(String key) {
        String[] values = getMessagesKey(key).split("\\f+");
        return values[new Random().nextInt(values.length)];
    }

    public static String getPatternsKey(String key) {
        return localizer.getPatterns().getString(key);
    }

    public static String getRandomPatternsKey(String key) {
        String[] values = getPatternsKey(key).split("\\f+");
        return values[new Random().nextInt(values.length)];
    }

    private InformationAccess() {};
}
