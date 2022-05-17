package dataStructures;

import lombok.Data;

import java.util.Objects;
import java.util.Stack;

public class LinkedList<T> {
    @Data
    private static class Node<T> {
        T element;
        Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    Node<T> head;

    /**
     * Creates a list of linked {@link Node} objects based on the given array of elements and returns a head of the list.
     *
     * @param elements an array of elements that should be added to the list
     * @param <T>      elements type
     * @return head of the list
     */
    public static <T> Node<T> createLinkedList(T... elements) {
        Objects.requireNonNull(elements);
        Node<T> current = new Node<>(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            Node<T> incomeNode = new Node<>(elements[i]);
            incomeNode.next = current;
            current = incomeNode;
        }
        return current;
    }

    /**
     * Prints a list in a reserved order using a recursion technique. Please note that it should not change the list,
     * just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedRecursively(Node<T> head) {
        if (head.next != null) {
            printReversedRecursively(head.next);
        }
        System.out.print(head.element);
    }


    /**
     * Prints a list in a reserved order using a {@link java.util.Stack} instance. Please note that it should not change
     * the list, just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedUsingStack(Node<T> head) {
        Stack<T> stack = new Stack<>();
        while (head != null) {
            stack.push(head.element);
            head = head.next;
        }

        while (!stack.empty()) {
            System.out.print(stack.pop());
        }
    }
}
