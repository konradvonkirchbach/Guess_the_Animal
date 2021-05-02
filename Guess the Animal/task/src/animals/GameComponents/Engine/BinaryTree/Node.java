package animals.GameComponents.Engine.BinaryTree;

import animals.GameComponents.Animal;
import animals.GameComponents.Fact;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
    private Animal animal;
    private Fact fact;
    @JsonManagedReference
    private Node left;
    @JsonManagedReference
    private Node right;
    @JsonBackReference
    private Node parent;

    public Node() {
        // empty constructor
    }

    public Node(Animal animal, Fact fact) {
        this.animal = animal;
        this.fact = fact;
    }

    public Node(Animal animal) {
        this.animal = animal;
    }

    public Node(Fact fact) {
        this.fact = fact;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Fact getFact() {
        return fact;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
