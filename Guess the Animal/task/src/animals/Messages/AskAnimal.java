package animals.Messages;

import animals.GameComponents.Animal;
import animals.GameComponents.Engine.UserInteraction;
import animals.GameInformation.InformationAccess;

public class AskAnimal implements Message {
    private Animal animal;

    public static Animal askFirstAnimal() {
        System.out.println(InformationAccess.getMessagesKey("animal.askFavorite"));
        return new Animal(UserInteraction.getUserInput());
    }

    public static Animal askSecondAnimal() {
        System.out.println("Enter the second animal:");
        return new Animal(UserInteraction.getUserInput());
    }

    public AskAnimal(Animal animal) {
        this.animal = animal;
    }

    @java.lang.Override
    public String getMessage() {
        if (!"eo".equals(InformationAccess.getLocalizer().getMessages().getLocale().getLanguage())) {
            return String.format("Is it %s?", animal.toString());
        } else {
            return "Ĉu ĝi estas " + animal + "?";
        }
    }
}
