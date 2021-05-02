package animals.GameComponents.Engine;

import animals.Actions.impl.Statement;
import animals.GameInformation.GameInformation;
import animals.GameInformation.InformationAccess;
import animals.Messages.Message;

import java.text.MessageFormat;

public class GameMaster {
    private Interogator interogator;

    public GameMaster(String format) {
        GameInformation gameInformation = InformationAccess.getGameInformation();
        gameInformation.setFileFormat(format);
        gameInformation.initTree();
        interogator = InformationAccess.getInterogator();
        init();
    }

    public void menu() {
        int option = 1;
        while (option != 0) {
            System.out.println(String.format("%s\n", InformationAccess.getMessagesKey("menu.property.title")));
            System.out.println(String.format("1. %s", InformationAccess.getMessagesKey("menu.entry.play")));
            System.out.println(String.format("2. %s", InformationAccess.getMessagesKey("menu.entry.list")));
            System.out.println(String.format("3. %s", InformationAccess.getMessagesKey("menu.entry.search")));
            System.out.println(String.format("4. %s", InformationAccess.getMessagesKey("menu.entry.statistics")));
            System.out.println(String.format("5. %s", InformationAccess.getMessagesKey("menu.entry.print")));
            System.out.println(String.format("0. %s", InformationAccess.getMessagesKey("menu.property.exit")));
            option = Integer.parseInt(UserInteraction.getUserInput());
            performMenueOption(option);
        }
        interogator.performAction(Statement.GOODBYE);
        InformationAccess.getGameInformation().saveDate();
    }

    public void play() {
        boolean play = true;
        while (play) {
            System.out.println(InformationAccess.getMessagesKey("game.think"));
            System.out.println(InformationAccess.getMessagesKey("game.enter"));
            UserInteraction.getUserInput();
            interogator.traverseTree();
            System.out.println(InformationAccess.getRandomMessagesKey("game.again"));
            play = InformationAccess.getInterpreter().isAnswerYes();
        }
    }

    private void init() {
        interogator.performAction(Statement.GREET);
        interogator.init();
    }

    private void performMenueOption(int option) {
        switch (option) {
            case 1:
                play();
                break;
            case 2:
                InformationAccess.getGameInformation().getGameTree().printAllAnimals();
                break;
            case 3:
                InformationAccess.getGameInformation().getGameTree().searchForAnAnimal();
                break;
            case 4:
                InformationAccess.getGameInformation().getGameTree().printStatistics();
                break;
            case 5:
                InformationAccess.getGameInformation().getGameTree().printKnowledgeTree();
                break;
            case 0:
                break;
            default:
                System.out.println(MessageFormat
                        .format(InformationAccess.getMessagesKey("menu.property.error"),0, 5));
        }
    }

}
