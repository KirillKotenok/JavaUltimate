package demo;

import java.util.concurrent.RecursiveAction;


public class ForkJoinSortingRecursiveAction<T extends Object & Comparable<? super T>> extends RecursiveAction {

    private T[] arr;

    public ForkJoinSortingRecursiveAction(T[] arr) {
        this.arr = arr;
    }

    @Override
    protected void compute() {
        if (arr.length > 1) {
            var leftArr = ((T[]) new Object[arr.length / 2]);
            var rightArr = ((T[]) new Object[arr.length / 2 + arr.length % 2]);
            System.arraycopy(arr, 0, leftArr, 0, arr.length / 2);
            System.arraycopy(arr, arr.length / 2, rightArr, 0, arr.length / 2 + arr.length % 2);
            var leftAction = new ForkJoinSortingRecursiveAction<T>(leftArr);
            var rightAction = new ForkJoinSortingRecursiveAction<T>(rightArr);
            rightAction.fork();
            leftAction.compute();
            rightAction.join();
            merge(leftArr, rightArr);
        }
    }

    private void merge(T[] left, T[] right) {
        var leftIndex = 0;
        var rightIndex = 0;
        while (left.length > leftIndex && right.length > rightIndex) {
            if (left[leftIndex].compareTo(right[rightIndex]) < 0) {
                arr[leftIndex + rightIndex] = left[leftIndex++];
            } else {
                arr[leftIndex + rightIndex] = right[rightIndex++];
            }
        }

        while (left.length > leftIndex) {
            arr[leftIndex + rightIndex] = left[leftIndex++];
        }

        while (right.length > rightIndex) {
            arr[leftIndex + rightIndex] = right[rightIndex++];
        }
    }
}
