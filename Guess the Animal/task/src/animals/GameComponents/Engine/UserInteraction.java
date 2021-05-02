package animals.GameComponents.Engine;

import java.util.Locale;
import java.util.Scanner;

public class UserInteraction {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getUserInput() {
        System.out.print("> ");
        return scanner.nextLine().toLowerCase(Locale.ROOT);
    }

    private UserInteraction() {}
}
