package Lab_8and9;

import java.util.ArrayList;

public class CareBinarySearchTree {

    ArrayList<Animal> array = new ArrayList<>();

    class Node {
        // Allowed to have up to 2 nodes
        int careLevel;
        ArrayList<Animal> animal;
        Node left;
        Node right;

        Node(int careLevel, Animal animal) {
            this.careLevel = careLevel;
            this.animal = new ArrayList<>();
            this.animal.add(animal);
            this.left = null;
            this.right = null;
        }
    }

    public Node root;

    public CareBinarySearchTree() {
        this.root = null;
    }

    public void insert(Animal animal) {
        root = insertRecursive(animal, root);
    }

    private Node insertRecursive(Animal animal, Node current) {
        if (current == null) {
            return new Node(animal.careLevel, animal);
        }
        if (animal.careLevel == current.careLevel) {
            current.animal.add(animal); // node exists
        } else if (animal.careLevel < current.careLevel) {
            current.left = insertRecursive(animal, current.left);
        } else {
            current.right = insertRecursive(animal, current.right);
        }
        return current;
    }

    public boolean search(int target) {
        return searchRecursive(target, root);
    }

    private boolean searchRecursive(int target, Node current) {
        if (current == null) {
            return false;
        }
        if (target == current.careLevel) {
            return true;

        } else if (target < current.careLevel) {
            return searchRecursive(target, current.left);

        } else {
            return searchRecursive(target, current.right);
        }
    }

    public void remove(String name, int careLevel) {
        root = removeRecursive(name, careLevel, root);
    }

    public Node removeRecursive(String name, int careLevel, Node current) {
        if (current == null) {
            return null;
        }

        if (careLevel < current.careLevel) {
            current.left = removeRecursive(name, careLevel, current.left);

        } else if (careLevel > current.careLevel) {
            current.right = removeRecursive(name, careLevel, current.right);

        } else {
            // Found the right node: remove the specific animal by name
            current.animal.removeIf(a -> a.name.equals(name));

            // If the list is now empty, remove the node itself
            if (current.animal.isEmpty()) {
                // Case 1: No children
                if (current.left == null && current.right == null) {
                    return null;
                }
                // Case 2: One child
                if (current.left == null)
                    return current.right;
                if (current.right == null)
                    return current.left;

                // Case 3: Two children — replace with in-order successor
                int inOrderSuccessor = findSmallest(current.right);
                current.careLevel = inOrderSuccessor;
                current.right = removeNode(inOrderSuccessor, current.right);
            }

        }
        return current;
    }

    // Removes a node by care level
    private Node removeNode(int careLevel, Node current) {
        if (current == null) {
            return null;
        }
        if (careLevel < current.careLevel) {
            current.left = removeNode(careLevel, current.left);
        } else if (careLevel > current.careLevel) {
            current.right = removeNode(careLevel, current.right);
        } else {
            // Case 1 & 2: no children or one child
            if (current.left == null)
                return current.right;
            if (current.right == null)
                return current.left;

            // Case 3: Two children
            int inOrderSuccessor = findSmallest(current.right);
            current.careLevel = inOrderSuccessor;
            current.right = removeNode(inOrderSuccessor, current.right);
        }
        return current;
    }

    private int findSmallest(Node subRoot) {
        while (subRoot.left != null) {
            subRoot = subRoot.left;
        }
        return subRoot.careLevel;
    }

    public void inOrder(Node current) {

        // Base case
        if (current != null) {
            inOrder(current.left);
            System.out.println(current.careLevel);
            inOrder(current.right);
        }
    }

    public void preOrder(Node current) {

        // Base case
        if (current != null) {
            System.out.println(current.careLevel);
            preOrder(current.left);
            preOrder(current.right);
        }
    }

    public void postOrder(Node current) {

        // Base case
        if (current != null) {
            postOrder(current.left);
            postOrder(current.right);
            System.out.println(current.careLevel);
        }
    }

    public ArrayList<Animal> getRange(int min, int max) {
        ArrayList<Animal> results = new ArrayList<>();
        getRangeHelper(root, min, max, results);
        return results;
    }

    private void getRangeHelper(Node current, int min, int max, ArrayList<Animal> results) {
        if (current == null) {
            return;
        }

        if (current.careLevel > min) {
            getRangeHelper(current.left, min, max, results); // Goes left
        }

        if (current.careLevel >= min && current.careLevel <= max) {
            results.addAll(current.animal);
        }

        if (current.careLevel < max) {
            getRangeHelper(current.right, min, max, results); // add: go right
        }
    }
}

// Worst Case Sceneario: O(n)
// Average Case Sceneario: O(log n)
