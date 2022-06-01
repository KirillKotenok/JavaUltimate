package dataStructuresAndAlgoritms;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class HashTable<K, V> {

    @SuppressWarnings({"unchecked", "rawtype"})
    private Node<K, V>[] arr = new Node[16];

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Adds an value to the hash table. Does not support duplicate elements.
     *
     * @param value
     * @return true if it was added
     */
    public boolean put(K key, V value) {
        int index = calculateHashCode(key);
        if (arr[index] == null) {
            arr[index] = new Node<K, V>(key, value);
            return true;
        } else if (!isInsideNodesEqualsByKey(arr[index], key)) {
            Node<K, V> income = new Node<K, V>(key, value);
            income.next = arr[index];
            arr[index] = income;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Accept a key and remove Node has this key. Print removed Node info and return true if operation
     * successful or return false if not.
     * @param key
     * @return
     */
    public boolean remove(K key) {
        int index = calculateHashCode(key);
        Node<K, V> currentNode = arr[index];
        if (currentNode == null) {
            System.out.printf("There are no elements by this key: %s", key);
            return false;
        } else if (currentNode.key.equals(key)) {
            System.out.printf("Removing %s : %s", currentNode.key, currentNode.value);
            arr[index] = currentNode.next;
            return true;
        } else {
            while (currentNode.next != null && !currentNode.next.key.equals(key)) {
                currentNode = currentNode.next;
            }
            if (currentNode.next == null) {
                System.out.printf("There are no elements by this key: %s", key);
                return false;
            } else {
                System.out.printf("Removing %s : %s", currentNode.next.key, currentNode.next.value);
                if (currentNode.next.next == null) {
                    currentNode.next = null;
                } else {
                    currentNode.next = currentNode.next.next;
                }
                return true;
            }
        }
    }

    private boolean isInsideNodesEqualsByKey(Node<K, V> current, K key) {
        if (isNull(current)) {
            return false;
        }
        if (!current.key.equals(key)) {
            return isInsideNodesEqualsByKey(current.next, key);
        } else {
            return true;
        }
    }

    /**
     * Prints a hash table according to the following format
     * 0: Andrii -> Taras
     * 1: Start
     * 2: Serhii
     * ...
     */
    public void printTable() {
        for (int i = 0; i < arr.length; i++) {
            printNodes(i);
        }
    }

    private void printNodes(int index) {
        Node<K, V> current = arr[index];
        if (current == null) {
            System.out.println(index + ": ");
            System.out.println("----------------------------");
            return;
        }
        System.out.print(index + ": ");
        while (current.next != null) {
            System.out.print(current.key + ":" + current.value + " -> ");
            current = current.next;
        }
        System.out.println(current.key + " : " + current.value);
        System.out.println("----------------------------");
    }

    /**
     * Creates a new underlying table with a given size and add all elements to the new table.
     *
     * @param newSize
     */
    public void resize(int newSize) {
        if (newSize < size()) {
            System.out.println("You may lost the data!!!");
            System.out.println("----------------------------");
        }
        arr = Arrays.copyOf(arr, newSize);
    }

    public int size() {
        return arr.length;
    }

    private int calculateHashCode(K key) {
        return Math.abs(key.hashCode() % arr.length);
    }
}
