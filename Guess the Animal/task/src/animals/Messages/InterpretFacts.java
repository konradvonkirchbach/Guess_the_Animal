package animals.Messages;

import animals.GameComponents.Animal;
import animals.GameInformation.GameInformation;
import animals.GameInformation.InformationAccess;

public class InterpretFacts implements Message {
    private final GameInformation gameInformation;

    public InterpretFacts() {
        gameInformation = InformationAccess.getGameInformation();
    }

    @Override
    public String getMessage() {
        Animal firstAnimal = gameInformation.getFirstAnimal();
        Animal secondAnimal = gameInformation.getSecondAnimal();
        return String.format("%s\n- %s %s\n- %s %s\n%s\n%s\n",
                InformationAccess.getMessagesKey("game.learned"),
                firstAnimal.withIndefiniteArticle(),
                firstAnimal.getFact().getWithoutPronoun(),
                secondAnimal.withIndefiniteArticle(),
                secondAnimal.getFact().getWithoutPronoun(),
                InformationAccess.getMessagesKey("game.distinguish"),
                !firstAnimal.getFact().isNegated() ? firstAnimal.getFact().turnIntoQuestion() : secondAnimal.getFact().turnIntoQuestion());

    }
}
