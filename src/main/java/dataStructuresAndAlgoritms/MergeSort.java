package dataStructuresAndAlgoritms;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MergeSort {
    public static void main(String[] args) {
        List<Integer> mutableList = new ArrayList<Integer>() {{
            add(1);
            add(25);
            add(3);
            add(15);
            add(0);
            add(2);
            add(2);
        }};
        List<Integer> unmodifiableList = Collections.unmodifiableList(mutableList);
        mergeSort(mutableList);
        mutableList.forEach(System.out::print);
    }

    public static <T extends Comparable<? super T>> Optional<List<T>> mergeSort(@NonNull List<T> comparableList) {
        if (isUnmodifiableList(comparableList)) {
            System.out.printf("\nThis is unmodifiable list!!!\n");
            return Optional.empty();
        } else {
            mergeSort(comparableList, 0, comparableList.size() - 1);
            return Optional.of(comparableList);
        }
    }

    private static <T extends Comparable<? super T>> void mergeSort(List<T> comparableList, int leftBorder, int rightBorder) {
        if (comparableList.subList(leftBorder, rightBorder).size() <= 1) {
            return;
        }
        var middle = (leftBorder + rightBorder) / 2;
        mergeSort(comparableList, leftBorder, middle);
        mergeSort(comparableList, middle+1, rightBorder);
        merge(comparableList, leftBorder, middle, rightBorder);
    }

    private static <T extends Comparable<? super T>> void merge(List<T> comparableList, int leftBorder, int middle, int rightBorder) {
        var firstPart = new Object[middle - leftBorder + 1];
        var secondPart = new Object[rightBorder - middle];
        var firstIndex = 0;
        var secondIndex = 0;

        initArr(comparableList, firstPart, secondPart, leftBorder, middle);

        while (firstIndex < firstPart.length && secondIndex < secondPart.length) {
            T firstElement = (T) firstPart[firstIndex];
            T secondElement = (T) secondPart[secondIndex];

            if (firstElement.compareTo(secondElement) <= 0) {
                comparableList.set(leftBorder++, firstElement);
                firstIndex++;
            } else {
                comparableList.set(leftBorder++, secondElement);
                secondIndex++;
            }
        }

        while (firstIndex < firstPart.length) {
            T element = (T) firstPart[firstIndex++];
            comparableList.set(leftBorder++, element);
        }

        while (secondIndex < secondPart.length) {
            T element = (T) secondPart[secondIndex++];
            comparableList.set(leftBorder++, element);
        }
    }

    private static <T extends Comparable<? super T>> void initArr(List<T> comparableList, Object[] firstPart, Object[] secondPart, int leftBorder, int middle) {
        for (int i = 0; i < firstPart.length; i++) {
            firstPart[i] = comparableList.get(leftBorder + i);
        }

        for (int j = 0; j < secondPart.length; j++) {
            secondPart[j] = comparableList.get(middle + j + 1);
        }
    }

    private static <T extends Comparable<? super T>> boolean isUnmodifiableList(List<T> comparableList) {
        return comparableList.getClass().getName().contains("UnmodifiableList");
    }
}
