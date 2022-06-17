package web.json;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Backson<T> {

    @SneakyThrows
    public Optional<T> readObject(Class<T> clazz, String json) {
        var clearJson = StringUtils.substringBetween(json, "{", "}")
                .replaceAll("\n", "");

        var fieldsMap = Arrays.stream(clearJson.split(","))
                .collect(Collectors.toMap(line -> line.split("\"")[1].toLowerCase(),
                        line -> line.split("\"")[3]));

        var returnedInstance = (T) clazz.getDeclaredConstructor().newInstance();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(PropertyName.class)) {
                declaredField.setAccessible(true);
                declaredField.set(returnedInstance, fieldsMap.get(declaredField.getAnnotation(PropertyName.class).value()));
            }
        }

        return Optional.of(returnedInstance);
    }
}
