package fundamentals.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MergeSortForkJoinRecursiveTask<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {
    private List<T> list;

    public MergeSortForkJoinRecursiveTask(List<T> list) {
        this.list = list;
    }

    @Override
    protected List<T> compute() {
        if (list.size() > 1) {
            var left = new ArrayList<>(list.subList(0, list.size() / 2));
            var right = new ArrayList<>(list.subList(list.size() / 2, list.size()));

            var leftTask = new MergeSortForkJoinRecursiveTask<>(left);
            var rightTask = new MergeSortForkJoinRecursiveTask<>(right);

            leftTask.fork();

            mergeResult(leftTask.join(), rightTask.compute());
        }
        return list;
    }

    private void mergeResult(List<T> leftResult, List<T> rightResult) {
        var leftIndex = 0;
        var rightIndex = 0;
        var resultIndex = 0;

        while (leftIndex < leftResult.size() && rightIndex < rightResult.size()) {
            if (leftResult.get(leftIndex).compareTo(rightResult.get(rightIndex)) < 0) {
                list.set(resultIndex++, leftResult.get(leftIndex++));
            } else {
                list.set(resultIndex++, rightResult.get(rightIndex++));
            }
        }

        while (leftIndex < leftResult.size()) {
            list.set(resultIndex++, leftResult.get(leftIndex++));
        }

        while (rightIndex < rightResult.size()) {
            list.set(resultIndex++, rightResult.get(rightIndex++));
        }
    }
}
