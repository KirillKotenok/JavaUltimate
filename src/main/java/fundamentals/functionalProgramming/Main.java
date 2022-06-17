package fundamentals.functionalProgramming;

import java.util.function.IntToDoubleFunction;
import java.util.function.LongConsumer;

public class Main {
    public static void main(String[] args) {
        LongConsumer longConsumer_1 = l -> System.out.println(l);
        longConsumer_1.accept(2l);

        LongConsumer longConsumer_2 = System.out::println;
        longConsumer_2.accept(4l);

        IntToDoubleFunction intToDoubleFunction_1 = i -> Double.valueOf(i);
        IntToDoubleFunction intToDoubleFunction_2 = Double::valueOf;
    }
}
