package dataStructures;

import static dataStructures.LinkedList.*;

public class Main {
    public static void main(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversedUsingStack(head);
        System.out.println();
        printReversedRecursively(head);
    }
}
