package animals.GameComponents.Engine;

import animals.Actions.Actions;
import animals.Actions.impl.Question;
import animals.Actions.impl.Statement;
import animals.GameComponents.Animal;
import animals.GameComponents.Engine.BinaryTree.Node;
import animals.GameComponents.Fact;
import animals.GameInformation.GameInformation;
import animals.GameInformation.InformationAccess;
import animals.Messages.*;

import java.text.MessageFormat;
import java.util.logging.Logger;

public class Interogator {

    private static final Logger LOGGER = Logger.getLogger(Interogator.class.getName());
    private GameInformation gameInformation = InformationAccess.getGameInformation();

    public void performAction(Actions action) {
        if (action.isQuestion()) {
            askQuestion(action);
        } else {
            makeStatement(action);
        }
    }

    public void init() {
        if (gameInformation.getGameTree().getRoot() == null) {
            System.out.println(String.format("\n%s", InformationAccess.getMessagesKey("animal.wantLearn")));
            Animal animal = AskAnimal.askFirstAnimal();
            Node node = new Node(animal);
            gameInformation.getGameTree().setRoot(node);
        }
        System.out.println(String.format("\n%s\n", InformationAccess.getMessagesKey("welcome")));
    }

    public void traverseTree() {
        // init tree
        Node node = gameInformation.getGameTree().getRoot();
        // initial case
        if (node == null) {
            LOGGER.info("Setting initial node");
            Animal animal = AskAnimal.askFirstAnimal();
            node = new Node(animal);
            gameInformation.getGameTree().setRoot(node);
        }
        // traverse the tree
        while (!node.isLeaf()) {
            LOGGER.info(String.format("Traversing node with Animal: %s\nand\nFact %s", node.getAnimal(), node.getFact()));
            System.out.println(node.getFact().turnIntoQuestion());
            if (InformationAccess.getInterpreter().isAnswerYes()) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        interpretLeaf(node);
    }

    private void interpretLeaf(Node leaf) {
        System.out.println(new AskAnimal(leaf.getAnimal()).getMessage());
        if (!InformationAccess.getInterpreter().isAnswerYes()) {
            System.out.println(InformationAccess.getMessagesKey("game.giveUp"));
            String userInput = UserInteraction.getUserInput();
            Animal correctAnimal = new Animal(userInput);
            gameInformation.setFirstAnimal(leaf.getAnimal());
            gameInformation.setSecondAnimal(correctAnimal);
            setFact(leaf, leaf.getAnimal(), correctAnimal);
        } else {
            System.out.println(InformationAccess.getRandomMessagesKey("game.win"));
        }
    }

    private void askQuestion(Actions action) throws IllegalArgumentException {
        Question question = (Question) action;
        LOGGER.info(String.format("Next action %s", question.name()));
        switch (question) {
            case ASK_FIRST_ANIMAL:
                gameInformation.setFirstAnimal(AskAnimal.askFirstAnimal());
                break;
            case ASK_SECOND_ANIMAL:
                gameInformation.setSecondAnimal(AskAnimal.askSecondAnimal());
                break;
            default:
                throw new IllegalArgumentException(String.format("No question %s found", question.name()));
        }
    }

    private void makeStatement(Actions action) throws IllegalArgumentException {
        Statement statement = (Statement) action;
        LOGGER.info(String.format("Next action %s", statement.name()));
        switch (statement) {
            case GREET:
                System.out.println(new Greeting().getMessage());
                break;
            case GOODBYE:
                System.out.println(new Goodbye().getMessage());
                break;
            case FACT:
                System.out.println(new InterpretFacts().getMessage());
                break;
            default:
                throw new IllegalArgumentException(String.format("No statement %s found", statement.name()));
        }
    }

    private void setFact(Node leaf, Animal guessed, Animal correct) {
        Fact fact = new Fact();
        fact.init();
        leaf.setFact(fact);
        while (true) {
            System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("game.isCorrect"), correct));
            String userInput = UserInteraction.getUserInput();
            if (InformationAccess.getInterpreter().answerIsYes(userInput)) {
                correct.setFact(fact);
                Fact falsified = new Fact(fact);
                falsified.setNegated(true);
                guessed.setFact(falsified);
                Node leftNode = new Node(correct);
                Node rightNode = new Node(guessed);

                leftNode.setParent(leaf);
                rightNode.setParent(leaf);

                leaf.setLeft(leftNode);
                leaf.setRight(rightNode);
                break;
            }
            if (InformationAccess.getInterpreter().answerIsNo(userInput)) {
                guessed.setFact(fact);
                Fact falsified = new Fact(fact);
                falsified.setNegated(true);
                correct.setFact(falsified);
                Node leftNode = new Node(guessed);
                Node rightNode = new Node(correct);

                leftNode.setParent(leaf);
                rightNode.setParent(leaf);

                leaf.setLeft(leftNode);
                leaf.setRight(rightNode);
                break;
            } else {
                System.out.println(new Clarification().getMessage());
            }
        }
        System.out.println(new InterpretFacts().getMessage());
    }

}
