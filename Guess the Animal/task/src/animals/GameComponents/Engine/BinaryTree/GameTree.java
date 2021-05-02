package animals.GameComponents.Engine.BinaryTree;

import animals.GameComponents.Engine.UserInteraction;
import animals.GameComponents.Fact;
import animals.GameInformation.InformationAccess;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameTree {
    private Node root;

    public GameTree() {
        // empty constructor
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void printAllAnimals() {
        System.out.println(InformationAccess.getMessagesKey("tree.list.animals"));
        List<String> allAnimals = new ArrayList<>();
        Consumer<Node> printIfAnimal = node -> {
            if (node.isLeaf()) {
                allAnimals.add(node.getAnimal().getName());
            }
        };
        preOrderTraversal(root, printIfAnimal);
        Collections.sort(allAnimals);
        allAnimals.forEach(animal -> System.out.println(" - " + animal));
    }

    public void searchForAnAnimal() {
        System.out.println(InformationAccess.getMessagesKey("animal.prompt"));
        String animal = UserInteraction.getUserInput();

        Predicate<Node> predicate = node ->
                animal.toLowerCase(Locale.ENGLISH).contains(node.getAnimal().getName()) && node.isLeaf();
        Function<Node, Node> identity = node -> node;

        Optional<Node> foundAnimal = preOrderTraversal(root, predicate, identity);
        Consumer<Node> ifFoundDo = node -> {
            System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.search.facts"), animal));
            List<Node> pathToNode = new ArrayList<>();
            pathToNode.add(node);
            Consumer<Node> addToList = n -> {
                if (n.getParent() == null) {
                    return;
                } else {
                    pathToNode.add(n.getParent());
                }
            };
            traverseParents(node, addToList);
            for (int i = pathToNode.size() - 1; i > 0; i--) {
                if (pathToNode.get(i).getLeft() == pathToNode.get(i - 1)) {
                    System.out.println(pathToNode.get(i).getFact());
                } else {
                    Fact negated = new Fact(pathToNode.get(i).getFact());
                    negated.setNegated(true);
                    System.out.println(negated);
                }
            }
        };
        foundAnimal.ifPresentOrElse(ifFoundDo,() ->
                System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.search.noFacts"), animal)));
    }

    public void printStatistics() {
        System.out.println(InformationAccess.getMessagesKey("tree.stats.title"));
        Statistics stats = new Statistics();
        stats.calculate(root, 0);
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.root"), (root.isLeaf() ? root.getAnimal() : root.getFact())));
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.nodes"), stats.numberOfNodes));
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.animals"), stats.numberOfAnimals));
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.statements"), (stats.numberOfNodes - stats.numberOfAnimals)));
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.height"), stats.maxHeight));
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.minimum"), stats.minDepth));
        System.out.println(MessageFormat.format(InformationAccess.getMessagesKey("tree.stats.average"), (stats.sumDepth / stats.numberOfAnimals)));
    }

    public void printKnowledgeTree() {
        String prefix =  "";
        printLevel(root, prefix);
    }

    private void printLevel(Node node, String prefix) {
        if (node == null)
            return;
        if (node == root) {
            System.out.println(InformationAccess.getMessagesKey("tree.print.corner") + " " + printNode(node));
            printLevel(node.getLeft(), prefix + "  ");
            printLevel(node.getRight(), prefix + "  ");
        } else {
            System.out.print(prefix);
            if (node.getParent().getLeft() == node) {
                System.out.println(InformationAccess.getMessagesKey("tree.print.branch") + " " + printNode(node));
                printLevel(node.getLeft(), prefix + InformationAccess.getMessagesKey("tree.print.vertical") + " ");
                printLevel(node.getRight(), prefix + InformationAccess.getMessagesKey("tree.print.vertical") + " ");
            } else {
                System.out.println(InformationAccess.getMessagesKey("tree.print.corner") + " " + printNode(node));
                printLevel(node.getLeft(), prefix + "  ");
                printLevel(node.getRight(), prefix + "  ");
            }
        }
    }

    private String printNode(Node node) {
        return node.isLeaf() ? node.getAnimal().toString() : node.getFact().turnIntoQuestion().replace("-", "").trim();
    }

    private void preOrderTraversal(Node node, Consumer<Node> consumer) {
        if (node != null) {
            consumer.accept(node);
            preOrderTraversal(node.getLeft(), consumer);
            preOrderTraversal(node.getRight(), consumer);
        }
    }

    private void traverseParents(Node node, Consumer<Node> consumer) {
        if (node != null) {
            consumer.accept(node);
            traverseParents(node.getParent(), consumer);
        }
    }

    private <T> Optional<T> preOrderTraversal(Node node,
                                              Predicate<Node> predicate,
                                              Function<Node, T> function) {
        if (node != null) {
            if (predicate.test(node)) {
                return Optional.of(function.apply(node));
            } else {
                Optional<T> leftChild = preOrderTraversal(node.getLeft(), predicate, function);
                Optional<T> rightChild = preOrderTraversal(node.getRight(), predicate, function);
                return leftChild.isPresent() ? leftChild : rightChild;
            }
        }
        return Optional.empty();
    }

    private static final class Statistics {
        int numberOfNodes = 0;
        int numberOfAnimals = 0;
        int maxHeight = 0;
        int minDepth = Integer.MAX_VALUE;
        double sumDepth = 0.0;

        void calculate(Node node, int height) {
            if (node != null) {
                numberOfNodes++;
                if (node.isLeaf()) {
                    numberOfAnimals++;
                    minDepth = Math.min(height, minDepth);
                    sumDepth += height;
                }
                maxHeight = Math.max(height, maxHeight);
                calculate(node.getLeft(), height + 1);
                calculate(node.getRight(), height + 1);
            }
        }
    }

}
