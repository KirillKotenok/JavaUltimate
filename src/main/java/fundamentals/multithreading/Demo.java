package fundamentals.multithreading;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Demo {

    public static void main(String[] args) {
        var list = generateArray(10);
        MergeSortForkJoinRecursiveTask<Integer> mergeSortForkJoinRecursiveTask = new MergeSortForkJoinRecursiveTask<>(list);
        List<Integer> sortedList = ForkJoinPool.commonPool().invoke(mergeSortForkJoinRecursiveTask);
        sortedList.forEach(System.out::println);
    }

    private static List<Integer> generateArray(int size) {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(size))
                .limit(size)
                .boxed()
                .collect(Collectors.toList());
    }
}
