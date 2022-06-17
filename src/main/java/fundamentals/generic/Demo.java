package fundamentals.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;

public class Demo {
    public static void main(String[] args) {
        Animal animal = new Animal("Zevs", 3);
        Cat cat = new Cat("Tushonka", 2.2);
        Dog dog = new Dog("Petya", 6);
        Jaguar jaguar = new Jaguar("Jetty", 10f);

        printName(cat);
        printName(jaguar);

        Collection<Animal> animals = new ArrayList<Animal>() {{
            add(cat);
            add(jaguar);
            add(dog);
            add(animal);
        }};

        printNames(animals, Animal::getName);
    }

    public static <T extends Cat> void printName(T animal) {
        System.out.println(animal.getName());
    }

    public static <T extends Animal, R> void printNames(Collection<? extends T> animal, Function<? super T, ? extends R> extractionFunction) {
        for (T a : animal) {
            System.out.println(extractionFunction.apply(a));
        }
    }

    private static <T, R extends Comparable<? super R>> Comparator<? super T> createComparatorComparing
            (Function<? super T, ? extends R> extractionFunction) {
        return (Comparator<T>) (o1, o2) -> {
            var first = extractionFunction.apply(o1);
            var second = extractionFunction.apply(o2);
            return first.compareTo(second);
        };
    }

    private static <T, R extends Comparable<? super R>> Comparator<? super T>
    composeComparatorThenComparing(Comparator<? super T> comparator, Function<? super T, ? extends R> extractionFunction) {
        return (Comparator<T>) (o1, o2) -> {
            int result = comparator.compare(o1, o2);
            return result == 0 ? extractionFunction.apply(o1).compareTo(extractionFunction.apply(o2)) : result;
        };
    }
}
