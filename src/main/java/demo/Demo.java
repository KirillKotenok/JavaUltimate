package demo;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Demo {

    @SneakyThrows
    public static void main(String[] args) {
        var pool = new ForkJoinPool();
        Double[] nonSortedArr = new Double[]{1.0, 2.1, 3d, 1.4, 5d, 26d, 14d, 74.0, 0.1, 42.42, 6.5, 7.6};
        pool.invoke(new ForkJoinSortingRecursiveAction<>(nonSortedArr));
        Arrays.stream(nonSortedArr).forEach(System.out::println);
    }

}
