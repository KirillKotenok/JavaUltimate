package fundamentals.dataStructuresAndAlgoritms;

import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class InsertionGenericSort {
    /*static <T> List<? extends T> insertionSort(List<? extends T> elements, Comparator<? super T> comparator) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(comparator);
        var currentIndex = 0;
        var pickedIndex = 0;
        var pickedElement = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            currentIndex = i - 1;
            pickedIndex = i;
            pickedElement = elements.get(i);
            while (comparator.compare(pickedElement, elements.get(currentIndex)) < 0 && currentIndex > 0) {
                elements.add(pickedIndex--, elements.get(currentIndex--));
            }
            elements.set(currentIndex, pickedElement);
        }
    }*/
}
