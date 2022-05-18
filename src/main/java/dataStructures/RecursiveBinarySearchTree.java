package dataStructures;

import java.util.Objects;
import java.util.Stack;
import java.util.function.Consumer;


public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;
    private int size = 0;

    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        public Node(T element) {
            this.element = element;
        }
    }

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> tree = new RecursiveBinarySearchTree<>();
        for (T element : elements) {
            tree.insert(element);
        }
        return tree;
    }

    @Override
    public boolean insert(T element) {
        Objects.requireNonNull(element);
        if (root == null) {
            root = new Node<>(element);
            size++;
            return true;
        } else {
            Node<T> income = new Node<>(element);
            return insert(income, root);
        }
    }

    private boolean insert(Node<T> income, Node<T> current) {
        if (income.element.compareTo(current.element) > 0) {
            if (current.right == null) {
                current.right = income;
                size++;
                return true;
            } else {
                return insert(income, current.right);
            }
        } else if (income.element.compareTo(current.element) < 0) {
            if (current.left == null) {
                current.left = income;
                size++;
                return true;
            } else {
                return insert(income, current.left);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);
        if (root == null) {
            return false;
        } else {
            return contains(element, root);
        }
    }

    private boolean contains(T element, Node<T> current) {
        if (current != null) {
            if (current.element.compareTo(element) == 0) {
                return true;
            } else {
                return contains(element, current.left) || contains(element, current.right);
            }
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        if (root == null) {
            return 0;
        } else {
            return Math.max(depth(root.right), depth(root.left));
        }
    }

    private int depth(Node<T> current) {
        if (current != null) {
            return 1 + Math.max(depth(current.left), depth(current.right));
        } else {
            return 0;
        }
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(consumer, root);
    }

    private void inOrderTraversal(Consumer<T> consumer, Node<T> current) {
        if (current.left != null) {
            inOrderTraversal(consumer, current.left);
        }
        consumer.accept(current.element);
        if (current.right != null) {
            inOrderTraversal(consumer, current.right);
        }
    }

    public void inOrderTraversalUseStack(Consumer<T> consumer) {
        Node<T> currentNode = root;
        Stack<Node<T>> stack = new Stack<>();
        while (currentNode != null || !stack.empty()) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
            Node<T> lastNode = stack.pop();
            consumer.accept(lastNode.element);
            currentNode = lastNode.right;
        }
    }
}
