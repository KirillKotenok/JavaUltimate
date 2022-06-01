package multithreading;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Demo {

    public static void main(String[] args) {
        var list = generateArray(100_000);
        MergeSortForkJoin<Integer> mergeSortForkJoin = new MergeSortForkJoin<>(list);
        List<Integer> sortedList = ForkJoinPool.commonPool().invoke(mergeSortForkJoin);
        sortedList.forEach(System.out::println);
    }

    private static List<Integer> generateArray(int size) {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(size))
                .limit(size)
                .boxed()
                .collect(Collectors.toList());
    }

    /*    public static String lock1 = "lock1";
    public static String lock2 = "lock2";

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                synchronized (lock2) {
                }
            }
        }, "Thread_1");

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                synchronized (lock1) {
                }
            }
        }, "Thread_2");

        Thread t3 = new Thread(() -> {
            while (true) {
                System.out.printf("Thread: %s - %s ", t1.getName(), t1.getState() + "\n");
                System.out.printf("Thread: %s - %s ", t2.getName(), t2.getState() + "\n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }, "Thread_3");

        t1.start();
        t2.start();
        t3.start();
    }*/
}
