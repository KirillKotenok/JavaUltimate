package reflectionAPI;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class RandomFieldComparator<T> implements Comparator<T> {

    private Class<?> clazz;
    private Field randomField;

    public RandomFieldComparator(Class<T> targetType) {
        clazz = targetType;
        initRandomField();
    }

    private void initRandomField() {
        Field[] comparableFields = Stream.of(clazz.getDeclaredFields())
                .filter(f -> f.getType().isPrimitive() || Comparable.class.isAssignableFrom(f.getType()))
                .toArray(Field[]::new);

        randomField = comparableFields[ThreadLocalRandom.current().nextInt(comparableFields.length)];
        randomField.setAccessible(true);
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value.
     *
     * @param o1
     * @param o2
     * @return
     */

    @SneakyThrows
    @Override
    public int compare(T o1, T o2) {
        if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        } else {
            Comparable field1 = (Comparable) randomField.get(o1);
            Comparable field2 = (Comparable) randomField.get(o2);
            return field1.compareTo(field2);
        }
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class %s is comparing %s", clazz.getName(), randomField.getName());
    }
}